`timescale 1ns / 1ps

module adder(
    input a,
    input b,
    input c_in,
    output c_out,
    output sum
);

    wire out_1, out_2, out_3, out_4, out_5, out_6, out_7;
    
    nor(out_1, a, b);
    nor(out_2, out_1, a);
    nor(out_3, out_1, b);
    nor(out_4, out_2, out_3);
    nor(out_5, out_4, c_in);
    nor(out_6, out_5, out_4);
    nor(out_7, out_5, c_in);
    
    nor(c_out, out_1, out_5);
    nor(sum, out_6, out_7);

endmodule
