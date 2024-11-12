`timescale 1ns / 1ps

module adder(
    input a,
    input b,
    input c
);
    wire not_a, not_b, y1, y2;
    nand(not_a, a, b);
    nand(not_b, b, b);
    
    nand(y1, not_a, b);
    nand(y2, a, not_b);
    
    nand(c, y1, y2);
endmodule

module four_adder(
    input a1,a2,a3,a4,
    input b1,b2,b3,b4,
    output ou1, ou2, ou3, ou4, cout
    );
    
    wire add1, add2;
    
    adder(add1,a1,b1);
endmodule
