// stepper motor pin for direction
const int dirPin = 2;
// stepper motor pin for one step
const int stepPin = 3;

bool notesAction[5];

void setup() {
  // initialize pins
  pinMode(stepPin, OUTPUT);
  pinMode(dirPin, OUTPUT);
  // initialize serial connection
  Serial.begin(115200);
  // initialize variables
  notesAction[0] = false;
  notesAction[1] = false;
  notesAction[2] = false;
  notesAction[3] = false;
  notesAction[4] = false;
}
void loop() {
  if(Serial.available() > 0) {
    String message = Serial.readStringUntil('\n');
    int delayInMicroseconds = getValue(message, " ", 0).toInt();
    String action = getValue(message, " ", 1);
    String track = getValue(message, " ", 2);
    Serial.println(delayInMicroseconds);
  }
}
void playNote(int delayInMicroseconds, uint32_t duration) {
  digitalWrite(dirPin, HIGH);
  // Play Note
  for(uint32_t tStart = millis(); (millis() - tStart) < duration;) {
    digitalWrite(stepPin, HIGH);
    delayMicroseconds(delayInMicroseconds);
    digitalWrite(stepPin, LOW);
    delayMicroseconds(delayInMicroseconds);
  }
}
String getValue(String data, char separator, int index) {
  int found = 0;
  int strIndex[] = { 0, -1 };
  int maxIndex = data.length() - 1;
  for (int i = 0; i <= maxIndex && found <= index; i++) {
    if (data.charAt(i) == separator || i == maxIndex) {
      found++;
      strIndex[0] = strIndex[1] + 1;
      strIndex[1] = (i == maxIndex) ? i + 1 : i;
    }
  }
  return found > index ? data.substring(strIndex[0], strIndex[1]) : "";
}
