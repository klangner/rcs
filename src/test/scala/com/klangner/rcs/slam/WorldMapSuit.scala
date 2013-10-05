package com.klangner.rcs.slam

import org.scalatest._

class WorldMapSpec extends FlatSpec with Matchers {

  "A map" should "remember its dimension" in {
    val world = new WorldMap(1,2)
    world.width should be (1)
    world.height should be (2)
  }
/*
  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[Int]
    a [NoSuchElementException] should be thrownBy {
      emptyStack.pop()
    } 
  }
  * 
  */
}