package com.klangner.ai4r


object Particle {
  val landmarks = List((20.0, 20.0), (80.0, 80.0), (20.0, 80.0), (80.0, 20.0))
                                                  //> landmarks  : List[(Double, Double)] = List((20.0,20.0), (80.0,80.0), (20.0,8
                                                  //| 0.0), (80.0,20.0))
  val robot = new Bot(30, 50, Math.PI/2)          //> robot  : com.klangner.ai4r.Bot = Position: (30,00, 50,00) Angle: 1,57
  val a = robot.turn(-Math.PI/2)                  //> a  : com.klangner.ai4r.Bot = Position: (30,00, 50,00) Angle: 0,04
  a.sense(landmarks)                              //> res0: List[Double] = List(31.622776601683793, 58.309518948453004, 31.6227766
                                                  //| 01683793, 58.309518948453004)
  val b = a.move(15)                              //> b  : com.klangner.ai4r.Bot = Position: (54,09, 51,01) Angle: 0,04
  b.sense(landmarks)                              //> res1: List[Double] = List(46.08156729703179, 38.8840191964809, 44.7519277557
                                                  //| 4055, 40.40721167772089)
 
  val c = b.turn(-Math.PI/2).move(10)             //> c  : com.klangner.ai4r.Bot = Position: (54,59, 40,11) Angle: -1,53
  c.sense(landmarks)                              //> res2: List[Double] = List(40.004270092402244, 47.302498037831526, 52.7990508
                                                  //| 9775211, 32.40568114608157)
                        
  val particles = new Particles(1000)             //> particles  : com.klangner.ai4r.Particles = com.klangner.ai4r.Particles@12872
                                                  //| 49
  println(particles.robots.head)                  //> Position: (0,00, 0,00) Angle: 0,00
}