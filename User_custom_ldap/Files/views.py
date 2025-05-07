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
from google.protobuf.json_format import MessageToDict
import json
import server_pb2 as grpc_pb2
import server_pb2_grpc as grpc_pb2_grpc

def recibir_url(lista_grpc_urls):
    url = "http://10.152.164.36:3000/api/pdf/"
    iteracion = 0
    nodos = ["10.152.164.37"]
    nodo = nodos[iteracion] + ":50051"

    channel = grpc.insecure_channel(nodo)
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
            return json.dumps({"error": str(e)})

    try:
        request = grpc_pb2.ConvertirUrlsRequest(urls=grpc_urls)
        response = stub.ConvertirUrls(request)
        
        # Procesar cada resultado para calcular el peso
        resultados_con_peso = []
        total_peso = 0
        
        for i, base64_str in enumerate(response.resultados):
            # Calcular peso en bytes (fórmula para base64)
            contenido_limpio = base64_str.replace("\n", "").replace("\r", "")
            longitud = len(contenido_limpio)
            relleno = contenido_limpio.count('=')
            peso_bytes = (longitud * 3 / 4) - relleno
            
            resultados_con_peso.append({
                "nombre": lista_grpc_urls[i]['nombre'],  # Asume mismo orden
                "contenido_base64": base64_str,
                "peso_bytes": round(peso_bytes, 2)
            })
            total_peso += peso_bytes

        # Enviar datos a tu API
        for item in resultados_con_peso:
            data = {
                "nombre": item['nombre'],
                "nodo": 1,
                "peso": item['peso_bytes'],  # Peso real calculado
                "tipo": lista_grpc_urls[i]['tipo']  # Añade tipo si es necesario
            }
            requests.post(url, json=data)

        # Rotación de nodos
        iteracion = (iteracion + 1) % len(nodos)

        return json.dumps({
            "resultados": resultados_con_peso,
            "total_peso_bytes": round(total_peso, 2)
        })

    except grpc.RpcError as e:
        error_msg = f"Error gRPC: {e.code()}: {e.details()}"
        print(error_msg)
        return json.dumps({"error": error_msg})

def recibir_archivo(lista_grpc_archivos):
    print("LLEGO 2")
    url = "http://10.152.164.36:3000/api/pdf/"
    iteracion = 0
    nodos = ["10.152.164.37"]
    nodo = nodos[iteracion] + ":50051"

    channel = grpc.insecure_channel(nodo)
    stub = grpc_pb2_grpc.ConvertidorOfficeStub(channel)

    try:
        # Construir la lista de ArchivoItem (corregido el nombre del campo)
        grpc_archivos = [
            grpc_pb2.ArchivoItem(
                nombre=item['nombre'],
                contenido_base64=item['contenido_base64'],
                tipo=item['tipo']
            )
            for item in lista_grpc_archivos  # Corregido el nombre del parámetro
        ]

        # Crear y enviar la request (corregido el nombre del campo)
        request = grpc_pb2.ConvertirArchivosRequest(archivos=grpc_archivos)
        response = stub.ConvertirArchivos(request)
        
        # Procesar la respuesta con cálculo de pesos
        resultados_con_peso = []
        total_peso = 0

        for i, base64_str in enumerate(response.resultados):
            # Calcular peso en bytes
            contenido_limpio = base64_str.replace("\n", "").replace("\r", "")
            longitud = len(contenido_limpio)
            relleno = contenido_limpio.count('=')
            peso_bytes = (longitud * 3 / 4) - relleno
            
            resultados_con_peso.append({
                "nombre": lista_grpc_archivos[i]['nombre'],
                "peso_bytes": round(peso_bytes, 2),
                "tipo": lista_grpc_archivos[i]['tipo']
            })
            total_peso += peso_bytes

            # Enviar a la API
            data = {
                "nombre": lista_grpc_archivos[i]['nombre'],
                "nodo": nodos.index(nodo.split(":")[0]) + 1,  # Nodo numérico
                "peso": round(peso_bytes, 2),
                "tipo": lista_grpc_archivos[i]['tipo']
            }
            requests.post(url, json=data)

        # Rotación de nodos
        iteracion = (iteracion + 1) % len(nodos)

        return json.dumps({
            "resultados": resultados_con_peso,
            "total_peso_bytes": round(total_peso, 2),
            "siguiente_nodo": nodos[iteracion]
        })

    except grpc.RpcError as e:
        error_msg = f"Error gRPC: {e.code()}: {e.details()}"
        print(error_msg)
        return json.dumps({"error": error_msg})
    
    except Exception as e:
        error_msg = f"Error inesperado: {str(e)}"
        print(error_msg)
        return json.dumps({"error": error_msg})