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

class SoapServiceUser(ServiceBase):

    @rpc(Unicode, _returns=Unicode)
    def mensajeSoap(ctx, mensaje):
        
        rta = enviar_mensaje(mensaje)
        
        return json.dumps({'mensaje': rta.respuesta})
    
    @rpc(Unicode, Unicode, Unicode, _returns=Unicode)
    def recibir_archivo_soap(ctx, file, nombre_archivo, tipo):

        rta = recibir_archivo(file, nombre_archivo, tipo)

        return json.dumps({'message': rta})
    
    @rpc(Unicode, Unicode, Unicode, _returns=Unicode)
    def recibir_url_soap(ctx, url, nombre_url, tipo):

        rta = recibir_archivo(url, nombre_url, tipo)

        return json.dumps({'message': rta})
    
my_soap = Application(
    [SoapServiceUser],
    tns='django.soap.files',
    in_protocol=Soap11(validator='lxml'),
    out_protocol=Soap11(),
)

def my_soap_consulta_files():
    
    django_app = DjangoApplication(my_soap)
    my_soap_app = csrf_exempt(django_app)
    
    return my_soap_app