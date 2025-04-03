from django.db import models
from django.contrib.auth.models import AbstractBaseUser, BaseUserManager

#clase Manager para la creacion de usuarios
class Manager_account(BaseUserManager):
    def create_user(self,name,email,last_name,username,answer,question,img,password=None,):
        
        if not email:
            raise ValueError('El usuario debe registrarse con un correo')
        
        user = self.model(
            email = self.normalize_email(email),
            name = name,
            last_name=last_name,
            username = username,
            answer = answer,
            question = question,
            img = img
        )
        
        user.set_password(password)
        user.save(using = self._db)
        return user
    
    def create_superuser(self,name,email,last_name,username,password):
        
        user = self.create_user(
            email = self.normalize_email(email),
            name = name,
            last_name=last_name,
            username = username
        )
        
        user.set_password(password)
        user.is_admin = True
        user.is_active = True
        user.is_staff = True
        user.is_superuser = True
        user.save(using = self._db)
        return user

#clase de usuario
class User(AbstractBaseUser):
    #atributos del usuario
    username = models.CharField(max_length=100,unique=True)
    name = models.CharField(max_length=100,null=False,blank=False)
    last_name = models.CharField(max_length=100,null=False,blank=False)
    email = models.EmailField(max_length=200,unique=True)
    img = models.CharField(max_length=200,null=True)
    question = models.CharField(max_length=100,default='Cual es el primer nombre de tu papa?')
    answer = models.CharField(max_length=50,null=True)

    
    #atributos de django
    date_joined = models.DateTimeField(auto_now_add=True)
    last_login = models.DateTimeField(auto_now_add=True)
    
    #roles de django
    is_admin = models.BooleanField(default=False)
    is_staff = models.BooleanField(default=False)
    is_superuser = models.BooleanField(default=False)
    is_active = models.BooleanField(default=True)
    
    USERNAME_FIELD = 'username'
    REQUIRED_FIELDS = ['name','last_name','email','answer']
    
    objects = Manager_account() #instanciamos la clase
    
    #valores a pintar al listar los usuarios
    def __str__(self):
        return self.username
    
    def has_perm(self,perm,obj=None):
        return self.is_superuser
    
    def has_module_perms(self,add_label):
        return True
    
    
class Request(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    request = models.BooleanField(default=True)