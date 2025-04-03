from django.urls import path
from .soap import *

urlpatterns = [
    path('files/', my_soap_consulta_files())
]