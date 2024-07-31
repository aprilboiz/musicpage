FROM python:3.11.9-bookworm AS builder
LABEL authors="Tuan Anh Phan"
EXPOSE 5000

WORKDIR /app/flask-service
COPY requirements.txt .

RUN --mount=type=cache,target=/root/.cache/pip \
    pip3 install -r requirements.txt

COPY . .
CMD [ "flask", "run", "-h", "0.0.0.0", "-p", "5000", "--debug" ]

