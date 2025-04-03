from rest_framework_simplejwt.authentication import JWTAuthentication
from django.contrib.auth import get_user_model
from django_auth_ldap.backend import LDAPBackend
from rest_framework_simplejwt.serializers import TokenObtainPairSerializer

#obtenemos el modelo del usuario
User = get_user_model()

#clase para generar los tokens
class CustomJWTAuthentication(JWTAuthentication):
    def authenticate(self, request):
        ldap_user = LDAPBackend().authenticate(request=request)#aqui nos autenticamos con el backend custom que creamos para los usuarios del LDAP
        if ldap_user is None:
            return None
    
        try:
            user = User.objects.get(username=ldap_user.username) #aqui obtenemos los datos del usuario
        #en caso de no existir dicho usuario en la base de datos de django se creara por si acaso el direcctorio activo se cae pueda seguir funcionando la app
        except User.DoesNotExist:
            user = User(
                username=ldap_user.username,
                email=ldap_user.email,
                name=ldap_user.name,
                last_name=ldap_user.last_name
            )
            user.set_unusable_password()
            user.save()
        
        return user, None

#Colocar el atributo is_admin en el token    
class MyTokenObtainPairSerializer(TokenObtainPairSerializer):
    
    @classmethod
    def get_token(cls, user):
        token = super().get_token(user)
        
        token['is_admin'] = user.is_admin
        
        return token

