`timescale 1ns / 1ps

module multi_adder(
    input a1, a2, a3, a4,
    input b1, b2, b3, b4,
    output sum1, sum2, sum3, sum4,
    output c_out
);
    
    wire c_out1, c_out2, c_out3;
    
    adder adder1 (.c_out(c_out1), .sum(sum1), .a(a1), .b(b1), .c_in(0));
    adder adder2 (.c_out(c_out2), .sum(sum2), .a(a2), .b(b2), .c_in(c_out1));
    adder adder3 (.c_out(c_out3), .sum(sum3), .a(a3), .b(b3), .c_in(c_out2));
    adder adder4 (.c_out(c_out), .sum(sum4), .a(a4), .b(b4), .c_in(c_out3));
endmodule