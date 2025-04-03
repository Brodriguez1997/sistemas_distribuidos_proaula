import pika, json

#conexion con CloudAMQP
#params = pika.URLParameters('')
#connection = pika.BlockingConnection(params)
#channel = connection.channel()
#print('conectado')
##funcion para publicar el mensaje
#def publish(method,body):
#    properties = pika.BasicProperties(method,delivery_mode=2) #anadir el delivery_mode=2 para que sea persistente
#    channel.basic_publish(exchange='',routing_key='perfil',body=json.dumps(body), properties=properties)