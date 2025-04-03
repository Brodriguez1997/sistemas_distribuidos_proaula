from django.urls import path
from rest_framework_simplejwt.views import TokenObtainPairView, TokenRefreshView
from .views import *
from .soap import *

urlpatterns = [
    path('token/', TokenObtainPairView.as_view(), name='token_obtain_pair'),  # Obtener token de acceso
    path('token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),  # Renovar token de acceso
    path('user/',UserView,name='user'),
    path('edit/',EditUserView,name='edit_user'), #vista para editar usuario
    path('logout/',logout_view,name='cerrar_sesion'),
    path('soap/', my_soap_consulta_users())
]
