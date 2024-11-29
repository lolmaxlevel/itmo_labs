`timescale 1ns / 1ps

module fifo_tb;

    // Параметры для теста
    parameter DEPTH = 8;
    parameter WIDTH = 3;

    // Сигналы
    reg clk;
    reg reset;
    reg push;
    reg pop;
    reg [7:0] data_in;
    wire [7:0] data_out;
    wire full;
    wire empty;
    wire [WIDTH-1:0] wr_debug; // Добавлен для отладки
    wire [WIDTH-1:0] rd_debug; // Добавлен для отладки

    // Инстанцирование модуля FIFO
    fifo #(
        .DEPTH(DEPTH),
        .WIDTH(WIDTH)
    ) uut (
        .clk(clk),
        .reset(reset),
        .push(push),
        .pop(pop),
        .data_in(data_in),
        .data_out(data_out),
        .full(full),
        .empty(empty),
        .wr_debug(wr_debug), // Подключение порта отладки
        .rd_debug(rd_debug)
    );

    // Генерация тактового сигнала
    initial begin
        clk = 0;
        forever #5 clk = ~clk; // Период тактового сигнала 10 нс
    end

    // Основной тест
    initial begin
        // Инициализация сигналов
        reset = 0;
        push = 0;
        pop = 0;
        data_in = 8'b0;

        // Сброс системы
        $display("reset signal...");
        reset = 1;
        #10;
        reset = 0;

        // Проверка начального состояния
        if (!empty || full) $display("Error: not valid rst flags");

        // Запись данных в FIFO
        $display("Writing to FIFO...");
        repeat (DEPTH) begin
            @(posedge clk);
            push = 1;
            data_in = $random; // Генерация случайных данных
            $display("Время: %0t, wr_debug: %0d, data_in: %0d", $time, wr_debug, data_in);
            @(posedge clk);
            push = 0; // Отключаем push
        end

        if (!full || empty) $display("Error: wrong flags after pushing");

        // Извлечение данных из FIFO
        $display("poping from FIFO...");
        repeat (DEPTH) begin
            @(posedge clk);
            pop = 1;
            $display("pop: %0t, rd_debug: %0d, data_out: %0d", $time, rd_debug, data_out);
            @(posedge clk);
            pop = 0;
        end

        if (full || !empty) $display("Error: wrong flags after poping");

        // Одновременные push и pop
        $display("push and pop at one time...");
        push = 1;
        pop = 1;
        data_in = 8'hAA;
        @(posedge clk);
        data_in = 8'hBB;
        @(posedge clk);
        push = 0;
        pop = 0;

        // Завершение симуляции
        $display("Test finished");
        $stop;
    end

endmodule

