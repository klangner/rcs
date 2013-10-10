package com.klangner.ai4r


object Particle {
  val landmarks = List((20.0, 20.0), (80.0, 80.0), (20.0, 80.0), (80.0, 20.0))
                                                  //> landmarks  : List[(Double, Double)] = List((20.0,20.0), (80.0,80.0), (20.0,8
                                                  //| 0.0), (80.0,20.0))
  val robot = new Bot(30, 50, Math.PI/2)          //> robot  : com.klangner.ai4r.Bot = Position: (30,00, 50,00) Angle: 1,57
  val a = robot.turn(-Math.PI/2)                  //> a  : com.klangner.ai4r.Bot = Position: (30,00, 50,00) Angle: -0,13
  a.sense(landmarks)                              //> res0: List[Double] = List(31.622776601683793, 58.309518948453004, 31.6227766
                                                  //| 01683793, 58.309518948453004)
  val b = a.move(15)                              //> b  : com.klangner.ai4r.Bot = Position: (39,77, 48,70) Angle: -0,13
  b.sense(landmarks)                              //> res1: List[Double] = List(34.850151330877274, 50.97210239631917, 37.02094206
                                                  //| 656409, 49.417993878647074)
 
  val c = b.turn(-Math.PI/2).move(10)             //> c  : com.klangner.ai4r.Bot = Position: (36,55, 35,24) Angle: -1,81
  c.sense(landmarks)                              //> res2: List[Double] = List(22.501772716335594, 62.377058667720824, 47.7211892
                                                  //| 811376, 46.042538125170175)
                                                
}