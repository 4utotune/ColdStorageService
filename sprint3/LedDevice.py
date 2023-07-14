#File: LedDevice.py
import RPi.GPIO as GPIO
import sys
import time
import threading
led_pin=25

GPIO.setmode(GPIO.BCM)
GPIO.setup(led_pin,GPIO.OUT)

print("Led operativo....")

# Variabile per indicare se il programma deve continuare ad eseguire il blink del LED
continua_blink = False

# Funzione per accendere il LED
def accendi_led():
    GPIO.output(led_pin, GPIO.HIGH)

# Funzione per spegnere il LED
def spegni_led():
    GPIO.output(led_pin, GPIO.LOW)


# Funzione per blinkare il LED
def blink_led():
    while True:
        while continua_blink:
            accendi_led()
            threading.Event().wait(0.5)  # Blink per 0.5 secondi acceso
            spegni_led()
            threading.Event().wait(0.5)  # Blink per 0.5 secondi spento
    

# Avvia il thread per il blink del LED
thread_led = threading.Thread(target=blink_led)
thread_led.start()

# Legge l'input da standard input
for line in sys.stdin:
    print(line)
    if '0' in line:
        continua_blink = False  # Ferma il blink del LED
        spegni_led()
    elif '1' in line:
        continua_blink = False  # Ferma il blink del LED
        accendi_led()
    elif '2' in line:
        continua_blink = True
    


