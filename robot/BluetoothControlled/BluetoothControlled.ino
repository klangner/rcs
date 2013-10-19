 
#include <SoftwareSerial.h>   //Software Serial Port
#define RxD 2
#define TxD 3
 
#define DEBUG_ENABLED  1
 
SoftwareSerial blueToothSerial(RxD,TxD);
String DEVICE_NAME = String("Condor7");
 
void setup() 
{ 
  int i;
  for(i=4;i<=7;i++)
    pinMode(i, OUTPUT); 
  Serial.begin(9600);
  pinMode(RxD, INPUT);
  pinMode(TxD, OUTPUT);
  setupBlueToothConnection();
  stop();
} 
 
void loop() 
{ 
  char val;
  while(1){
    if(blueToothSerial.available()){
      val = blueToothSerial.read();
     //if(Serial.available()){
      //char val = Serial.read();
      executeCommand(val);
    }
  }
} 
 
void setupBlueToothConnection()
{
  blueToothSerial.begin(38400); //Set BluetoothBee BaudRate to default baud rate 38400
  blueToothSerial.print("\r\n+STWMOD=0\r\n"); //set the bluetooth work in slave mode
  blueToothSerial.print("\r\n+STNA=" + DEVICE_NAME + "\r\n"); //set the bluetooth name 
  blueToothSerial.print("\r\n+STOAUT=1\r\n"); // Permit Paired device to connect me
  blueToothSerial.print("\r\n+STAUTO=0\r\n"); // Auto-connection should be forbidden here
  delay(2000); // This delay is required.
  blueToothSerial.print("\r\n+INQ=1\r\n"); //make the slave bluetooth inquirable 
  Serial.println("The slave bluetooth is inquirable!");
  delay(2000); // This delay is required.
  blueToothSerial.flush();
}

//Standard PWM DC control
int E1 = 5;     //M1 Speed Control
int E2 = 6;     //M2 Speed Control
int M1 = 4;    //M1 Direction Control
int M2 = 7;    //M1 Direction Control
int SPEED = 200;
 
void executeCommand(char command)
{
  switch(command)
  {
      case 'f'://Move Forward
        forward(SPEED);   //move forward in max speed
        break;
      case 'b'://Move Backward
        backward(SPEED);   //move back in max speed
        break;
      case 'l'://Turn Left
        turnLeft(SPEED);
        break;      
      case 'r'://Turn Right
        turnRight(SPEED);
        break;
      case 'p':
        ping();
        break;
      case 's':
        stop();
        break;
  }
}
 
 
void stop(void)                    //Stop
{
  digitalWrite(E1,LOW);  
  digitalWrite(E2,LOW);  
  Serial.println("Stop");  
}  
void forward(char speed)          //Move forward
{
  analogWrite (E1,speed);      //PWM Speed Control
  digitalWrite(M1,HIGH);   
  analogWrite (E2,speed);   
  digitalWrite(M2,HIGH);
  Serial.println("Forward");  
} 
void backward(char speed)          //Move backward
{
  analogWrite (E1,speed);
  digitalWrite(M1,LOW);  
  analogWrite (E2,speed);   
  digitalWrite(M2,LOW);
  Serial.println("Backward");  
}
void turnLeft(char speed)             //Turn Left
{
  analogWrite (E1,speed);
  digitalWrite(M1,LOW);   
  analogWrite (E2,speed);   
  digitalWrite(M2,HIGH);
  Serial.println("Left");  
}

void turnRight(char speed)             //Turn Right
{
  analogWrite (E1,speed);
  digitalWrite(M1,HIGH);   
  analogWrite (E2,speed);   
  digitalWrite(M2,LOW);
  Serial.println("Right");  
}

void ping()         
{
  Serial.println("Ping");  
  blueToothSerial.println("pong");
}


