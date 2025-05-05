FROM python:3.9-slim
ENV PYTHONUNBUFFERED 1
WORKDIR /app
RUN apt-get update && \
    apt-get install -y libldap2-dev libsasl2-dev
    
RUN apt-get update && apt-get install -y \
    gcc \
    libc-dev \
    python3-dev

COPY requirements.txt /app/requirements.txt
RUN pip install -r requirements.txt
RUN pip install protobuf==3.20.0

COPY . /app
