from django.views.decorators.csrf import csrf_exempt
from spyne.application import Application
from spyne.decorator import rpc
from spyne.model.primitive import Unicode, Integer, Double, String, DateTime, Date
from spyne.protocol.soap import Soap11
from spyne.server.django import DjangoApplication
from spyne.service import ServiceBase
import json
from spyne import Iterable, Array
from spyne import ComplexModel
from django.forms.models import model_to_dict
from django.db import IntegrityError
from spyne.error import ResourceNotFoundError
from spyne.model.fault import Fault
from django.db.models.deletion import ProtectedError
from .views import *
from django.http import HttpResponse, HttpRequest
from django.db.models import Sum
from spyne.error import ResourceNotFoundError
import os
from .models import *
from collections import OrderedDict
import base64
from xml.etree import ElementTree as ET
from rest_framework_simplejwt.tokens import RefreshToken

class FIleSoapModel(ComplexModel):
    __namespace__ = "files"
    id = Integer
    file = String

class ArchivoSoap(ComplexModel):
    __namespace__ = "Files.soap"
    nombre = Unicode
    contenido = Unicode 
    tipo = Unicode 

class UrlSoap(ComplexModel):
    __namespace__ = "Files.soap"
    nombre = Unicode
    url = Unicode
    tipo = Unicode

class SoapServiceUser(ServiceBase):
    
    @rpc(Array(ArchivoSoap), _returns=Unicode)
    def recibir_archivos_soap(ctx, archivos):
        
        lista_grpc = []

        for archivo in archivos:
            arhivo_data = {
                'nombre': archivo.nombre,
                'tipo': archivo.tipo,
                'contenido_base64': archivo.contenido
            }
            lista_grpc.append(arhivo_data)

        rta = recibir_archivo(lista_grpc)

        return json.dumps({'resultados': rta})
    
    @rpc(Array(UrlSoap), _returns=Unicode)
    def recibir_url_soap(ctx, urls):
        
        lista_urls = []

        for url in urls:
            url_data = {
                'nombre': url.nombre,
                'tipo': url.tipo,
                'url': url.url
            }
            lista_urls.append(url_data)

        rta = recibir_url(lista_urls)

        return json.dumps({'message': rta})
    
my_soap = Application(
    [SoapServiceUser],
    tns='Files.soap',
    in_protocol=Soap11(validator='lxml'),
    out_protocol=Soap11(),
)

def my_soap_consulta_files():
    
    django_app = DjangoApplication(my_soap)
    my_soap_app = csrf_exempt(django_app)
    
    return my_soap_app