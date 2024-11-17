package com.yushu.exercise11;


import com.yushu.exercise30.Exercise30_08;

class Checkings extends Exercise30_08.Account {
  protected int overdraftLimit = 5000;

  @Override
  public String toString() {
    return "Checkings";
  }
}

class Saving extends Exercise30_08.Account {
  protected int overdraftLimit = 5000;

  @Override
  public String toString() {
    return "Saving";
  }
}
