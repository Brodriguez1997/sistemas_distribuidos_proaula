# Generated by Django 4.1 on 2023-08-31 02:40

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Users', '0002_user_answer_user_img_user_question'),
    ]

    operations = [
        migrations.AlterField(
            model_name='user',
            name='img',
            field=models.CharField(max_length=200, null=True),
        ),
    ]
