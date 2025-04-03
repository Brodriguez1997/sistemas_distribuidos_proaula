from django.test import TestCase
from django.urls import reverse
from rest_framework import status
from rest_framework.test import APIClient
from .banned_words import banned_words
#ESTO ES PARA EL CORREO
from .models import User
from django.contrib.auth import get_user_model
from django.contrib.sites.shortcuts import get_current_site
from django.core.mail import EmailMessage
from django.template.loader import render_to_string
from django.utils.http import urlsafe_base64_encode
from django.utils.encoding import force_bytes
from django.contrib.auth.tokens import default_token_generator
from colorama import Fore, Style, init

user = get_user_model()

# Inicializa colorama
init()

# Símbolo de check y texto
check_symbol = "\u2713"
text = "Correcto"

#creamos una clase para pruebas de login que hereda de TesCase
class LoginTest(TestCase):
    
    #este metodo setUp se ejecuta antes de las pruebas para configurar el entorno
    def setUp(self):
        self.client = APIClient() #usamos el cliente de pruebas que trae DRF

    #la primera prueba de login es con datos correctos
    def test_login_success(self):
        #para la prueba usamos la url de login
        url = reverse('token_obtain_pair')
        
        #datos de prueba
        data = {'username': 'Administrador', 'password': 'Adminupb1234'}

        #hacemos la solicitud post a la url
        response = self.client.post(url, data, format='json')

        #verificamos que la respuesta sea correcta
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        
        #verificamos que la respuesta tenga las claves de acceso y refresh
        self.assertTrue('access' in response.data)
        self.assertTrue('refresh' in response.data)
        
        #imprimimos si la respuesta es exitosa
        print('Prueba para login exitoso')
        print(f"{Fore.GREEN}{check_symbol} Prueba exitosa: se obtuvo un token de acceso y refresh para el usuario Administrador.")
        print('-------------------------------------------------------------------------------------')

    #la segunda prueba de login es con datos falsos
    def test_login_failure(self):
        #para la prueba usamos la url de login
        url = reverse('token_obtain_pair')
        
        #datos de prueba
        data = {'username': 'admin', 'password': 'admin'}

        #hacemos la solicitud post a la url
        response = self.client.post(url, data, format='json')

        #verificamos que la respuesta sea correcta
        self.assertEqual(response.status_code, status.HTTP_401_UNAUTHORIZED)
        
        #verificamos que la respuesta no tenga las claves
        self.assertFalse('access' in response.data)
        self.assertFalse('refresh' in response.data)
        
        #imprimimos los resultados de la prueba
        print(f'{Fore.GREEN}{check_symbol}Prueba para login no exitoso')
        print(f"{Fore.GREEN}{check_symbol}Prueba exitosa: se recibió un error de autorización para datos incorrectos.")
        print(f'{Style.RESET_ALL}---------------------------------------------------------------------------')
        
#creamos una clase para pruebas de registro que hereda de Tescase
class RegisterTest(TestCase):
    
    #este metodo setUp se ejecuta antes de las pruebas para configurar el entorno
    def setUp(self):
        self.client = APIClient() #usamos el cliente de pruebas que trae DRF
    
    #prueba exitosa de registro con datos correctos
    def test_register_success(self):
        #para la prueba usamos la url de registro
        url = reverse('create_user')
        
        #datos de prueba
        data = {
            'username': 'nuevo_usuario',
            'name': 'Nombre',
            'last_name': 'Apellido',
            'email': 'correo@gmail.com',
            'password': 'contraseña',
            'img': 'imagen',
            'answer': 'respuesta',
            'question': 'pregunta'
        }
        
        #verificamos que la respuesta sea correcta
        response = self.client.post(url, data, format='json')

        #verificamos que las respuestas sean correctas
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(user.objects.count(), 1)
        
        #imprimimos las respuestas
        print(f"{Fore.GREEN}{check_symbol}Prueba para registro con datos correctos")
        print(f"{Fore.GREEN}{check_symbol} Prueba exitosa: los datos del usuario registrado son validos.")
        print(f"{Style.RESET_ALL}-------------------------------------------------------------")
    
    #prueba exitosa de registro con correo duplicado
    def test_register_duplicate_email(self):
        #para la prueba usamos la url de registro
        url = reverse('create_user')
        
        #creamos un usuario en la base de datos para verificar
        User.objects.create_user(
            username = 'usuario',
            name = 'Nombre',
            last_name = 'Apellido',
            email = 'monoconchudo18@gmail.com',
            password = 'Password1234#',
            img = 'imagen',
            answer = 'respuesta',
            question = 'pregunta'
        )
        
        #datos de prueba
        data = {
            'username': 'nuevo_usuario',
            'name': 'Nombre',
            'last_name': 'Apellido',
            'email': 'monoconchudo18@gmail.com',
            'password': 'contraseña',
            'img': 'imagen',
            'answer': 'respuesta',
            'question': 'pregunta'
        }
        
        #verificamos que la respuesta sea correcta
        response = self.client.post(url, data, format='json')

        #verificamos que las respuestas sean correctas
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        self.assertEqual(user.objects.count(), 1)
        
        #imprimimos las respuestas
        print(f"{Fore.GREEN}{check_symbol}Prueba para registro con correo duplicado")
        print(f"{Fore.GREEN}{check_symbol}Prueba exitosa: Se verifico que el correo ya esta registrado por lo tanto no puede guardarse")
        print(f"{Style.RESET_ALL}--------------------------------------------------------------------------------------------")
        
    #prueba exitosa de registro con username duplicado
    def test_register_duplicated_username(self):
        #para la prueba usamos la url de registro
        url = reverse('create_user')
        
        #creamos un usuario en la base de datos para verificar
        User.objects.create_user(
            username = 'usuario',
            name = 'Nombre',
            last_name = 'Apellido',
            email = 'correo@gmail.com',
            password = 'Password1234#',
            img = 'imagen',
            answer = 'respuesta',
            question = 'pregunta'
        )
        
        #datos de prueba
        data = {
            'username': 'usuario',
            'name': 'Nombre',
            'last_name': 'Apellido',
            'email': 'correo2@gmail.com',
            'password': 'contraseña',
            'img': 'imagen',
            'answer': 'respuesta',
            'question': 'pregunta'
        }
        
        #verificamos que la respuesta sea correcta
        response = self.client.post(url, data, format='json')

        #verificamos que las respuestas sean correctas
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        self.assertEqual(user.objects.count(), 1)
        
        #imprimimos las respuestas
        print(f"{Fore.GREEN}{check_symbol}Prueba para registro con username duplicado")
        print(f"{Fore.GREEN}{check_symbol}Prueba exitosa: Se verifico que el username ya esta registrado por lo tanto no puede guardarse")
        print(f"{Style.RESET_ALL}--------------------------------------------------------------------------------------------")
    
    #prueba exitosa de registro con username con palabra prohibida
    def test_register_banned_word(self):
        #para la prueba usamos la url de registro
        url = reverse('create_user')
        
        #palabras prohibidas de prueba
        banned_word = 'puto'
        banned_words.append(banned_word)
        
        #datos de prueba
        data = {
            'username': 'gonorrea',
            'name': 'Nombre',
            'last_name': 'Apellido',
            'email': 'correo2@gmail.com',
            'password': 'contraseña',
            'img': 'imagen',
            'answer': 'respuesta',
            'question': 'pregunta'
        }
        
        #verificamos que la respuesta sea correcta
        response = self.client.post(url, data, format='json')

        #verificamos que las respuestas sean correctas
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        self.assertEqual(user.objects.count(), 0)
        
        #imprimimos las respuestas
        print(f"{Fore.GREEN}{check_symbol}Prueba para registro con username con palabra prohibida")
        print(f"{Fore.GREEN}{check_symbol}Prueba exitosa: Se verifico que el username contiene una palabra prohibida, por lo tanto no se crea.")
        print(f"{Style.RESET_ALL}--------------------------------------------------------------------------------------------")
        
