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

def recibir_url(lista_grpc_urls):
    #url = "http://host.docker.internal:3000/api/pdf/"

    channel = grpc.insecure_channel('192.168.0.226:50051')
    stub = grpc_pb2_grpc.ConvertidorUrlsStub(channel)

    grpc_urls = []
    for item in lista_grpc_urls:
        try:
            grpc_item = grpc_pb2.UrlItem(
                nombre=item['nombre'],
                url=item['url'],
                tipo=item['tipo']
            )
            grpc_urls.append(grpc_item)
        except Exception as e:
            print("Error construyendo item gRPC:", e)

    # Enviar al servidor gRPC
    try:
        print(grpc_urls)
        request = grpc_pb2.ConvertirUrlsRequest(urls=grpc_urls)
        response = stub.ConvertirUrls(request)
        print("Respuesta gRPC:", response)
    except grpc.RpcError as e:
        print("Error en llamada gRPC:")
        print("Code:", e.code())
        print("Details:", e.details())
        return "Error gRPC"
    
    if response:
        for urlDaTa in lista_grpc_urls:
            data = {
                "nombre": item['nombre'],
                "ruta": 10,
                "tamano": 1000,
            }
            #response_db = requests.post(url, json=data)
            #print(response_db)

    return "recibido"

def recibir_archivo(lista_grpc_archivos):
    
    #url = "http://host.docker.internal:3000/api/pdf/"

    channel = grpc.insecure_channel('192.168.0.226:50051')
    stub = grpc_pb2_grpc.ConvertidorOfficeStub(channel)

    grpc_archivos = [
        grpc_pb2.UrlItem(
            nombre=item['nombre'],
            contenido_base64=item['contenido_base64'],
            tipo=item['tipo']
        )
        for item in lista_grpc_urls
    ]

    request = grpc_pb2.ConvertirArchivosRequest(archivos=grpc_archivos)
    response = stub.ConvertirArchivos(request)
    print(response)
    
    if response:
        for archivo in lista_grpc_archivos:
            data = {
                "nombre": archivo.nombre,
                "ruta": 10,
                "tamano": 1000,
            }
            #response_db = requests.post(url, json=data)
            #print(response_db)

    return "recibido"