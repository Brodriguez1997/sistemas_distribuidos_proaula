from rest_framework.views import APIView
from django.shortcuts import render,redirect
from rest_framework.response import Response
from rest_framework import status
from rest_framework.permissions import IsAuthenticated, IsAdminUser
from .custom_ldap_backend import create_user_with_ldap, add_user_to_group_in_ldap, edit_user_in_ldap, remove_user_from_group_in_ldap
from rest_framework.decorators import api_view, permission_classes
from django.core.mail import EmailMessage
from django.utils.http import urlsafe_base64_encode, urlsafe_base64_decode
from django.contrib.auth.tokens import default_token_generator
from django.contrib.sites.shortcuts import get_current_site
from django.template.loader import render_to_string
from django.utils.encoding import force_bytes
from django.conf import settings
from .serializers import UserSerializer, RequestSerializer
from django.contrib.auth import get_user_model,logout
from .models import User, Request
from django.contrib.auth.decorators import login_required
from .banned_words import banned_words
from django.contrib.auth import authenticate, login
from rest_framework.pagination import PageNumberPagination
from rest_framework.generics import ListAPIView
import requests
from rest_framework.permissions import IsAuthenticated
from rest_framework_simplejwt.authentication import JWTAuthentication

#vista para ediatr el usuario
@api_view(['POST'])
@login_required
def EditUserView(request):
    user = request.user
    new_name = request.data.get('name')
    new_last_name = request.data.get('last_name')
    
    # Editar los campos en la base de datos de Django
    user.name = new_name
    user.last_name = new_last_name
    user.save()
    
    #mandar los datos actualizados al ldap
    edit_user_in_ldap(user,new_name,new_last_name)
    return Response({'message': 'Información de usuario actualizada exitosamente.'}, status=status.HTTP_200_OK)
            

#vista de usuario
@api_view(['GET'])
@permission_classes([IsAuthenticated])
def UserView(request):
    user = request.user
    serialzier = UserSerializer(user)
    return Response(serialzier.data, status=status.HTTP_200_OK)

##en proceso logout
@api_view(['POST'])
@permission_classes([])
def logout_view(request):
    logout(request)
    return Response({'message': 'Logged out successfully'}, status=status.HTTP_200_OK)

#################SOAP#################
def registerSoapView(username, name, last_name, email, password, img, answer, question):
    
    serializers = UserSerializer(
        data={
            'username':username,
            'name':name,
            'last_name':last_name,
            'email':email,
            'password':password,
            'img':img,
            'answer':answer,
            'question':question
        }
    )
    
    print(username)
    
    if serializers.is_valid():
        username = serializers.validated_data.get('username')
        name = serializers.validated_data.get('name')
        last_name = serializers.validated_data.get('last_name')
        email = serializers.validated_data.get('email')
        password = serializers.validated_data.get('password')
        img = serializers.validated_data.get('img')
        answer = serializers.validated_data.get('answer')
        question = serializers.validated_data.get('question')
        
        user = create_user_with_ldap(username, name, last_name, email,img,question,answer, password)
        print(user)
        if user:
            return {'message': "ok"}
        else:
            return {'error_message': "bad"}
    print(serializers.errors)
    return serializers.errors