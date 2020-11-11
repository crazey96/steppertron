// stepper motor pin for direction
const int dirPin = 2;
// stepper motor pin for one step
const int stepPin = 3;

// frequencies converted into 50% duty cycle delay (microseconds)
// time period = 1 / frequency
// full delay (microseconds) = time period * 1000000
// delay (50% duty cycle) = full delay / 2;
// complete calculation: delay = ((1 / frequency) * 1000000) / 2
const int c8 = 119;
const int b7 = 126;
const int a_7 = 134;
const int a7 = 142;
const int g_7 = 150;
const int g7 = 159;
const int f_7 = 168;
const int f7 = 178;
const int e7 = 189;
const int d_7 = 200;
const int d7 = 212;
const int c_7 = 225;
const int c7 = 238;
const int b6 = 253;
const int a_6 = 268;
const int a6 = 284;
const int g_6 = 300;
const int g6 = 318;
const int f_6 = 337;
const int f6 = 357;
const int e6 = 379;
const int d_6 = 401;
const int d6 = 425;
const int c_6 = 450;
const int c6 = 477;
const int b5 = 506;
const int a_5 = 536;
const int a5 = 568;
const int g_5 = 601;
const int g5 = 637;
const int f_5 = 675;
const int f5 = 715;
const int e5 = 758;
const int d_5 = 803;
const int d5 = 851;
const int c_5 = 901;
const int c5 = 955;
const int b4 = 1012;
const int a_4 = 1072;
const int a4 = 1136;
const int g_4 = 1203;
const int g4 = 1275;
const int f_4 = 1351;
const int f4 = 1431;
const int e4 = 1516;
const int d_4 = 1607;
const int d4 = 1702;
const int c_4 = 1803;
const int c4 = 1911;
const int b3 = 2024;
const int a_3 = 2145;
const int a3 = 2272;
const int g_3 = 2407;
const int g3 = 2551;
const int f_3 = 2702;
const int f3 = 2863;
const int e3 = 3033;
const int d_3 = 3214;
const int d3 = 3405;
const int c_3 = 3607;
const int c3 = 3822;
const int b2 = 4049;
const int a_2 = 4290;
const int a2 = 4545;
const int g_2 = 4815;
const int g2 = 5102;
const int f_2 = 5405;
const int f2 = 5726;
const int e2 = 6067;
const int d_2 = 6428;
const int d2 = 6810;
const int c_2 = 7215;
const int c2 = 7644;
const int b1 = 8099;
const int a_1 = 8580;
const int a1 = 9090;
const int g_1 = 9631;
const int g1 = 10204;
const int f_1 = 10810;
const int f1 = 11453;
const int e1 = 12134;
const int d_1 = 12856;
const int d1 = 13620;
const int c_1 = 14430;
const int c1 = 15289;
const int b0 = 16198;
const int a_0 = 17161;
const int a0 = 18181;
const int g_0 = 19262;
const int g0 = 20408;
const int f_0 = 21621;
const int f0 = 22907;
const int e0 = 24269;
const int d_0 = 25713;
const int d0 = 27242;
const int c_0 = 28861;
const int c0 = 30578;

void setup() {
  pinMode(stepPin, OUTPUT);
  pinMode(dirPin, OUTPUT);
}
void loop() {
  // c-major
  playNote(c4, 500L);
  playNote(d4, 500L);
  playNote(e4, 500L);
  playNote(f4, 500L);
  playNote(g4, 500L);
  playNote(a4, 500L);
  playNote(b4, 500L);
  playNote(c5, 500L);
  playNote(d5, 500L);
  playNote(e5, 500L);
  playNote(f5, 500L);
  playNote(g5, 500L);
  playNote(a5, 500L);
  playNote(b5, 500L);
  playNote(c6, 500L);
  playNote(d6, 500L);
  playNote(e6, 500L);
  playNote(f6, 500L);
  playNote(g6, 500L);
  playNote(a6, 500L);
  playNote(b6, 500L);
  playNote(c7, 500L);
  playNote(d7, 500L);
  playNote(e7, 500L);
  playNote(f7, 500L);
  playNote(g7, 500L);
  playNote(a7, 500L);
  playNote(b7, 500L);
}
void playNote(int note, uint32_t duration) {
  digitalWrite(dirPin, HIGH);
  // Play Note
  for(uint32_t tStart = millis(); (millis() - tStart) < duration;) {
    digitalWrite(stepPin, HIGH);
    delayMicroseconds(note);
    digitalWrite(stepPin, LOW);
    delayMicroseconds(note);
  }
}
