package com.klangner.ai4r

import scala.util.Random

class Bot(_x : Double, _y: Double, alpha :Double) {
  
  val x = _x
  val y = _y
  val angle = alpha
  val forwardNoise = 5.0
  val turnNoise    = 0.1
  val senseNoise   = 5.0
  
  def turn(alpha :Double) :Bot = {
    val noise = Random.nextGaussian()*turnNoise
    new Bot(x, y, angle + alpha + noise)
  }
  
  def move(distance :Double) :Bot = {
    val dist = distance + Random.nextGaussian()*forwardNoise
    val dx = dist * Math.cos(angle)
    val dy = dist * Math.sin(angle)
    new Bot(x+dx, y+dy, angle)
  }

  def sense(landmarks :List[(Double, Double)]) :List[Double] = {
    if(landmarks.isEmpty) List()
    else{
    	val (posX, posY) = landmarks.head
    	val dx = Math.pow(x-posX, 2)
    	val dy = Math.pow(y-posY, 2)
    	val dist = Math.sqrt(dx + dy) + Random.nextGaussian()*senseNoise
    	Math.sqrt(dx + dy) :: sense(landmarks.tail)
    }
  }

  override def toString = "Position: (%.2f, %.2f) Angle: %.2f".format(x, y, angle) 

}


class Particles(count: Integer){
  val robots = for (i <- 1 to count) yield new Bot(0, 0, 0)
}