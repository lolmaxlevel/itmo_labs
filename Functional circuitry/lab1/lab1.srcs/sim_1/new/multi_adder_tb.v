`timescale 1ns / 1ps

module multi_adder_tb;
    reg a1, a2, a3, a4;
    reg b1, b2, b3, b4;
    
    wire sum1, sum2, sum3, sum4;
    wire c_out;
    
    multi_adder uut (
        .a1(a1), .a2(a2), .a3(a3), .a4(a4),
        .b1(b1), .b2(b2), .b3(b3), .b4(b4),
        .sum1(sum1), .sum2(sum2), .sum3(sum3), .sum4(sum4),
        .c_out(c_out)
    );

    integer i, j;
    reg [3:0] a;   // 4-битная переменная для хранения значений a1-a4
    reg [3:0] b;   // 4-битная переменная для хранения значений b1-b4
    reg [4:0] expected_sum; // Ожидаемая сумма

    initial begin
        // Цикл для перебора всех возможных комбинаций значений 4-битных чисел a и b
        for (i = 0; i < 16; i = i + 1) begin
            for (j = 0; j < 16; j = j + 1) begin
                a = i;
                b = j;
                
                // Присваиваем значения для каждого из битов a и b
                {a4, a3, a2, a1} = a;
                {b4, b3, b2, b1} = b;
                
                expected_sum = a + b;

                #10

                // Проверяем правильность результата
                if ({c_out, sum4, sum3, sum2, sum1} == expected_sum) begin
                    $display("Correct: a=%b, b=%b => sum=%b, c_out=%b", a, b, {sum4, sum3, sum2, sum1}, c_out);
                end else begin
                    $display("Error: a=%b, b=%b => sum=%b (expected %b), c_out=%b (expected %b)", 
                             a, b, {sum4, sum3, sum2, sum1}, expected_sum[3:0], c_out, expected_sum[4]);
                end
            end
        end
    end
endmodule
