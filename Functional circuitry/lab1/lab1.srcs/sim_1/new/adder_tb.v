`timescale 1ns / 1ps

module adder_tb;
    reg a_in, b_in, c_in;
    wire sum, c_out;

    adder adder_1 (
        .a(a_in),
        .b(b_in),
        .c_in(c_in),
        .sum(sum),
        .c_out(c_out)
    );

    integer i;
    reg [2:0] test_val;      
    reg expected_sum;
    reg expected_c_out;

    initial begin
        for (i = 0; i < 8; i = i + 1) begin
            test_val = i;
            a_in = test_val[0];
            b_in = test_val[1];
            c_in = test_val[2];

            // Вычисляем ожидаемые значения суммы и бита переноса
            expected_sum = (a_in ^ b_in) ^ c_in;
            expected_c_out = (a_in & b_in) | (c_in & (a_in ^ b_in));

            #10 // ждем

            // Проверяем соответствие реальных и ожидаемых значений
            if (sum == expected_sum && c_out == expected_c_out) begin
                $display("Correct: a_in=%b, b_in=%b, c_in=%b => sum=%b, c_out=%b", a_in, b_in, c_in, sum, c_out);
            end else begin
                $display("Error: a_in=%b, b_in=%b, c_in=%b => sum=%b (expected %b), c_out=%b (expected %b)", 
                         a_in, b_in, c_in, sum, expected_sum, c_out, expected_c_out);
            end
        end
    end
endmodule
