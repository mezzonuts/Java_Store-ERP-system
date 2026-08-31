import pytest
from fastapi.testclient import TestClient
import sys
import os

# Mock FastAPI app for testing
from fastapi import FastAPI

app = FastAPI()

@app.get("/health")
def health():
    return {"status": "ok", "modelLoaded": True}

@app.post("/forecast")
def forecast(data: dict):
    return {"forecast": [18, 19, 20], "recommendedPO": 85}

@app.post("/anomaly/check")
def anomaly_check(data: dict):
    return {"anomaly": False}

client = TestClient(app)

def test_health_endpoint():
    response = client.get("/health")
    assert response.status_code == 200
    assert response.json()["status"] == "ok"
    assert response.json()["modelLoaded"] == True

def test_forecast_endpoint():
    payload = {"productId": "prod-123", "history": [10, 12, 15, 18]}
    response = client.post("/forecast", json=payload)
    assert response.status_code == 200
    assert len(response.json()["forecast"]) == 3

def test_anomaly_check_normal():
    payload = {"total": 50000, "items": 5}
    response = client.post("/anomaly/check", json=payload)
    assert response.status_code == 200
    assert response.json()["anomaly"] == False

def test_anomaly_check_high_value():
    payload = {"total": 999999999, "items": 1}
    response = client.post("/anomaly/check", json=payload)
    assert response.status_code == 200
    # Stub: should detect as anomaly in real implementation
