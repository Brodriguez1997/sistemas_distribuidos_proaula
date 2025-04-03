from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from .custom_ldap_backend import create_user_with_ldap
from .models import User

class CustomUserAdmin(UserAdmin):
    filter_horizontal = ()
    list_display = ['username', 'email', 'name', 'last_name','is_superuser','is_active']
    list_filter = ['is_staff', 'is_superuser', 'is_active']
    search_fields = ('username', 'email', 'name', 'last_name')
    ordering = ('username',)
    fieldsets = (
        (None, {'fields': ('username', 'password')}),
        ('Personal info', {'fields': ('name', 'last_name', 'email')}),
        ('Permissions', {'fields': ('is_active', 'is_staff', 'is_superuser')}),
    )
    add_fieldsets = (
        (None, {
            'classes': ('wide',),
            'fields': ('username', 'password1', 'password2', 'name', 'last_name', 'email', 'is_staff', 'is_superuser','is_active'),
        }),
    )
    def save_model(self,request,obj,form,change):
        create_user_with_ldap(obj.username,obj.name,obj.last_name,obj.email,form.cleaned_data['password1'])
        super().save_model(request,obj,form,change)

admin.site.register(User,CustomUserAdmin)