// to store motor states
typedef struct MotorState {
  // note stores the delay in microseconds
  int note;
  // action stores if motor is on or off
  bool action;
  bool high;
  long previousTime;
};
MotorState motorStates[5];
// custom key-value pair array to store notes
struct Note {
  char* noteName;
  int delayInMicroseconds;
};
const Note notes[] = {
  {"C8", 119},
  {"B7", 126},
  {"A#7", 134},
  {"A7", 142},
  {"G#7", 150},
  {"G7", 159},
  {"F#7", 168},
  {"F7", 178},
  {"E7", 189},
  {"D#7", 200},
  {"D7", 212},
  {"C#7", 225},
  {"C7", 238},
  {"B6", 253},
  {"A#6", 268},
  {"A6", 284},
  {"G#6", 300},
  {"G6", 318},
  {"F#6", 337},
  {"F6", 357},
  {"E6", 379},
  {"D#6", 401},
  {"D6", 425},
  {"C#6", 450},
  {"C6", 477},
  {"B5", 506},
  {"A#5", 536},
  {"A5", 568},
  {"G#5", 601},
  {"G5", 637},
  {"F#5", 675},
  {"F5", 715},
  {"E5", 758},
  {"D#5", 803},
  {"D5", 851},
  {"C#5", 901},
  {"C5", 955},
  {"B4", 1012},
  {"A#4", 1072},
  {"A4", 1136},
  {"G#4", 1203},
  {"G4", 1275},
  {"F#4", 1351},
  {"F4", 1431},
  {"E4", 1516},
  {"D#4", 1607},
  {"D4", 1702},
  {"C#4", 1803},
  {"C4", 1911},
  {"B3", 2024},
  {"A#3", 2145},
  {"A3", 2272},
  {"G#3", 2407},
  {"G3", 2551},
  {"F#3", 2702},
  {"F3", 2863},
  {"E3", 3033},
  {"D#3", 3214},
  {"D3", 3405},
  {"C#3", 3607},
  {"C3", 3822},
  {"B2", 4049},
  {"A#2", 4290},
  {"A2", 4545},
  {"G#2", 4815},
  {"G2", 5102},
  {"F#2", 5405},
  {"F2", 5726},
  {"E2", 6067},
  {"D#2", 6428},
  {"D2", 6810},
  {"C#2", 7215},
  {"C2", 7644},
  {"B1", 8099},
  {"A#1", 8580},
  {"A1", 9090},
  {"G#1", 9631},
  {"G1", 10204},
  {"F#1", 10810},
  {"F1", 11453},
  {"E1", 12134},
  {"D#1", 12856},
  {"D1", 13620},
  {"C#1", 14430},
  {"C1", 15289},
  {"B0", 16198},
  {"A#0", 17161},
  {"A0", 18181},
  {"G#0", 19262},
  {"G0", 20408},
  {"F#0", 21621},
  {"F0", 22907},
  {"E0", 24269},
  {"D#0", 25713},
  {"D0", 27242},
  {"C#0", 28861},
  {"C0", 30578}
};
// stepper motor pin for direction
const int dirPin = 2;
// stepper motor pin for one step
const int stepPin[5] = { 3, 4, 5, 6, 7 };

void setup() {
  // initialize pins
  pinMode(dirPin, OUTPUT);
  for(int index = 0 ; index < 5; index++)
  {
    pinMode(stepPin[index], OUTPUT);
  }
  // initialize serial connection
  Serial.begin(115200);
}

void loop() {
  if(Serial.available() > 0) {
    parseSerialMessage(Serial.readStringUntil('\n'));
  }
  playMotor(0);
  playMotor(1);
  playMotor(2);
  playMotor(3);
  playMotor(4);
}
void playMotor(int motor) {
  digitalWrite(dirPin, HIGH);
  if(motorStates[motor].action) {
   if(motorStates[motor].high) {
    digitalWrite(stepPin[motor], HIGH);
    if ((micros() - motorStates[motor].previousTime) >= motorStates[motor].note){
      motorStates[motor].high = false;
      motorStates[motor].previousTime = micros();
    }
   } else {
    digitalWrite(stepPin[motor], LOW);
    if ((micros() - motorStates[motor].previousTime) >= motorStates[motor].note){
      motorStates[motor].high = true;
      motorStates[motor].previousTime = micros();
    }
   }
  }
}
void playNotes() {
  digitalWrite(dirPin, HIGH);
  for (int track = 0; track < 5; track++) {
    if(motorStates[track].action) {
      // Play Note
      digitalWrite(stepPin[track], HIGH);
      delayMicroseconds(motorStates[track].note);
      digitalWrite(stepPin[track], LOW);
      delayMicroseconds(motorStates[track].note);
    }
  }
}
void parseSerialMessage(String message) {
  // split message
  String note = splitMessage(message, ' ', 0);
  String action = splitMessage(message, ' ', 1);
  int motor = splitMessage(message, ' ', 2).toInt();
  // set note (delay)
  int delayInMicroseconds = getDelayInMicroseconds(note);
  motorStates[motor].note = delayInMicroseconds;
  motorStates[motor].high = true;
  motorStates[motor].previousTime = micros();
  // set action
  if(action.equals("true")) {
    motorStates[motor].action = true;
  } else {
    motorStates[motor].action = false;
  }
}
String splitMessage(String data, char separator, int index) {
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
int getDelayInMicroseconds(String note) {
  for (int i = 0; i < 97; i++) {
    if(note.equals(notes[i].noteName)) {
      return notes[i].delayInMicroseconds;
    }
  }
  return 0;
}
