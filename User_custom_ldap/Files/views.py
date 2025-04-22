from rest_framework.views import APIView
from django.shortcuts import render,redirect
from rest_framework.response import Response
from rest_framework import status
from rest_framework.permissions import IsAuthenticated, IsAdminUser
from rest_framework.decorators import api_view, permission_classes
from .models import *
from django.contrib.auth.decorators import login_required
from django.contrib.auth import authenticate, login
from rest_framework.permissions import IsAuthenticated
from rest_framework.parsers import FileUploadParser, MultiPartParser, FormParser
import requests
from rest_framework.renderers import TemplateHTMLRenderer
from django.conf import settings
import os
import jwt
from django.db import transaction
from django.db.models import F
from django.http import HttpResponse
import mimetypes
from rest_framework.decorators import parser_classes
import base64
import re
import grpc
import json
import server_pb2 as grpc_pb2
import server_pb2_grpc as grpc_pb2_grpc

def enviar_mensaje(mensaje):
    print(mensaje)
    url = "http://host.docker.internal:3000/api/hola"

    response = requests.get(url)
    
    if response.status_code == 200:
        print("Respuesta del servidor:", response.text)
    else:
        print(f"Error: {response.status_code} - {response.text}")
        
    channel = grpc.insecure_channel('host.docker.internal:50051')
    stub = grpc_pb2_grpc.ConvertidorOfficeStub(channel)
    
    request = grpc_pb2.SaludarRequest(mensaje=mensaje)
    response = stub.Saludar(request)

    print(response)

    return response

def recibir_url(url, nombre_url, tipo):
    
    url = "http://host.docker.internal:3000/api/pdf/"

    channel = grpc.insecure_channel('host.docker.internal:50051')
    stub = grpc_pb2_grpc.ConvertidorUrlsStub(channel)

    request = grpc_pb2.ConvertirUrlsRequest(url=url, nombre=nombre_url, tipo=tipo)
    response = stub.ConvertirUrls(request)
    print(response)
    
    if response:
        data = {
            "nombre": nombre_url,
            "ruta": 10,
            "tamano": 1000,
        }
        response_db = requests.post(url, json=data)
        print(response_db)

    return "recibido"

def recibir_archivo(file, nombre_archivo, tipo):
    
    url = "http://host.docker.internal:3000/api/pdf/"

    channel = grpc.insecure_channel('host.docker.internal:50051')
    stub = grpc_pb2_grpc.ConvertidorOfficeStub(channel)

    request = grpc_pb2.ConvertirArchivosRequest(archivo=file, nombre=nombre_archivo, tipo=tipo)
    response = stub.ConvertirArchivos(request)
    print(response)
    
    if response:
        data = {
            "nombre": nombre_archivo,
            "ruta": 10,
            "tamano": 1000,
        }
        response_db = requests.post(url, json=data)
        print(response_db)

    return "recibido"