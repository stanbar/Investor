package com.stasbar.compoundinterest

import junit.framework.Assert
import org.junit.Test


/**
 * Created by stasbar on 13.07.2017
 */
class CompoundMathTest{

    @Test
    fun compoundTest1(){
        val P = 5000.0
        val r = 0.05
        val n = 12
        val t = 10

        val result = CompoundMath.calc(P,r,n,t)
        Assert.assertEquals(8235.0474884514,result)
    }
}