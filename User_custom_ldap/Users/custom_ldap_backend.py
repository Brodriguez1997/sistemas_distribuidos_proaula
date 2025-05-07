from django.contrib.auth.backends import ModelBackend
from django.contrib.auth import get_user_model
from django_auth_ldap.backend import LDAPBackend
from django_auth_ldap.config import LDAPSearch
from ldap3.extend.microsoft.addMembersToGroups import ad_add_members_to_groups
import ldap3
import paramiko
from ldap3 import Server, Connection, ALL, MODIFY_REPLACE,SUBTREE,ALL_ATTRIBUTES


#obtenemos el usuario personalizado
User = get_user_model()

#creamos una clase para la autenticacion de usuarios de LDAP
class CustomLDAPBackend(LDAPBackend, ModelBackend):
    def authenticate(self, request, username=None, password=None, **kwargs):
        ldap_user = super().authenticate(request, username, password, **kwargs)#usamos el usernmae y password para autenticar
        if ldap_user is None:
            return None
            
        try:
            user = User.objects.get(username=username)
        #crear un nuevo usuario si no existe en la base de datos local
        except User.DoesNotExist:
            user_attrs = self.get_user_attrs(ldap_user, username)
            user = User(
                username=username,
                email=user_attrs.get('email'),
                name=user_attrs.get('name'),
                last_name=user_attrs.get('last_name')
            )
            user.set_password(password)
            user.save()
        
        user.is_active = True
        user.save()
    
        return user

    #obtenemos los atributos del usuario
    def get_user_attrs(self, ldap_user, username):
        attrs = super().get_user_attrs(ldap_user, username)
        user_attrs = {
            'email': attrs.get('email', ''),
            'name': attrs.get('name', ''),
            'last_name': attrs.get('last_name', ''),
        }
        return user_attrs

    def get_user(self, user_id):
        try:
            return User.objects.get(pk=user_id)
        except User.DoesNotExist:
            return None

#funcion para crear usuarios en el LDAP
def create_user_in_ldap(username,name,last_name,email,img,question,answer,password):
    ssh_client = paramiko.SSHClient()
    ssh_client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    # Conectarse a la máquina virtual por SSH
    ssh_client.connect('10.152.190.14', username='Administrador', password='Jagua456')
    #192.168.1.72
    # Ejecutar el script de PowerShell remotamente
    #shell = ssh_client.invoke_shell()
    try:
        #Import-Module ActiveDirectory
        #command_to_execute = f'powershell -NoProfile -ExecutionPolicy Bypass -Command "{powershell_script}"'
        stdin, stdout, stderr = ssh_client.exec_command(f'powershell -NoProfile -ExecutionPolicy Bypass -Command New-ADUser -userPrincipalName {username} -GivenName {name} -Surname {last_name} -EmailAddress {email} -Name {username} -sAMAccountName {username} -Description {img} -AccountPassword (ConvertTo-SecureString {password} -AsPlainText -Force) -Enabled $true -ChangePasswordAtLogon $false -PasswordNeverExpires $true' , bufsize = -1 , timeout = None , get_pty = True , environment = None)    
        # Leer la salida y el error para consumirlos y descartarlos
        exit_status = stdout.channel.recv_exit_status()
        
        # Cerrar los archivos de salida
        stdout.close()
        stderr.close()
    except:
        print('comando inutil')
    
    #shell.close()
    ssh_client.close()

#funcion que permite crear un usuario desde django y invoca la funcion anterior para mandar los datos al LDAP
def create_user_with_ldap(username,name,last_name,email,img,question,answer,password):
    #crea el usuario en Django
    try:
        user = User.objects.get(email=email)
        user.username = username
        user.name = name
        user.last_name = last_name
        user.img = img
        user.answer = answer
        user.question = question
        user.set_password(password)
        user.save()
    except User.DoesNotExist:
        user = User.objects.create_user(
            username=username,
            name=name,
            last_name=last_name,
            email=email,
            img=img,
            password=password,
            question=question,
            answer=answer
        )
    
    #crea el usuario en LDAP
    create_user_in_ldap(username, name, last_name, email,img,question,answer, password)
    return user

    

#funcion para agregar al usuario a un grupo
def add_user_to_group_in_ldap(username):
    server_uri = "ldap://10.152.190.14:389"
    bind_dn = "CN=Administrador,CN=Users,DC=nexus,DC=upb"
    bind_password = "Adminupb1234#"
    
    #configura la conexión LDAP
    server = ldap3.Server(server_uri)
    conn = ldap3.Connection(server, user=bind_dn, password=bind_password)
    conn.bind()

    user_dn = f'CN={username},CN=Users,DC=nexus,DC=upb'
    group_dn = "CN=django-users,CN=Users,DC=nexus,DC=upb"
    
    ad_add_members_to_groups(conn,user_dn,group_dn)
    
    conn.unbind()
    
#funcion para remover un usuario d eun grupo
def remove_user_from_group_in_ldap(username):
    server_uri = "ldap://210.152.190.14:389"
    bind_dn = "CN=Administrador,CN=Users,DC=nexus,DC=upb"
    bind_password = "Adminupb1234#"

    # Configura la conexión LDAP
    server = ldap3.Server(server_uri)
    conn = ldap3.Connection(server, user=bind_dn, password=bind_password)
    conn.bind()

    user_dn = f'CN={username},CN=Users,DC=nexus,DC=upb'
    group_dn = "CN=django-users,CN=Users,DC=nexus,DC=upb"

    # Elimina al usuario del grupo
    conn.modify(group_dn, {'member': [(ldap3.MODIFY_DELETE, [user_dn])]})

    conn.unbind()


#funcion para editar la informacion general de un usuario
def edit_user_in_ldap(username,new_name,new_last_name):
    server_uri = "ldap://10.152.190.14:389"
    bind_dn = "CN=Administrador,CN=Users,DC=distribuidos,DC=com"
    bind_password = "Jagua456"
    
    #configura la conexión LDAP
    server = ldap3.Server(server_uri)
    conn = ldap3.Connection(server, user=bind_dn, password=bind_password)
    conn.bind()
    
    #obtenemos el usuario que queremos editar
    user_dn = f'CN={username},CN=Users,DC=distribuidos,DC=com'
    
    #aplicamos los cambios
    changes = {
        'givenName': [(ldap3.MODIFY_REPLACE, [new_name])],
        'sn': [(ldap3.MODIFY_REPLACE, [new_last_name])],
    }
    
    conn.modify(user_dn, changes)

    conn.unbind()
    
    