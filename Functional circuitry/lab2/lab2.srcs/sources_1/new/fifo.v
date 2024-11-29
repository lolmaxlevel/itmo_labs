`timescale 1ns / 1ps

module fifo (
    input wire clk,              // Системный тактовый сигнал. Он контрлирует выровненность сигналов. Если сигналы не выравнены, то тогда ничего не делаем
    input wire reset,            // Сигнал сброса
    input wire push,             // Сигнал добавления данных
    input wire pop,              // Сигнал извлечения данных
    input wire [7:0] data_in,    // Входные данные (8 бит)
    output reg [7:0] data_out,   // Выходные данные (8 бит)
    output reg full,             // Флаг полной очереди
    output reg empty,            // Флаг пустой очереди
    output wire [WIDTH-1:0] wr_debug, // Дополнительный порт для отладки
    output wire [WIDTH-1:0] rd_debug // Дополнительный порт для отладки
);

    // Параметры
    parameter DEPTH = 8;         // Глубина очереди (количество элементов)
    parameter WIDTH = 3;     // Размер указателей (log2(DEPTH))

    // Регистры
    reg [7:0] mem [DEPTH-1:0];   // Память для хранения данных
    reg [WIDTH-1:0] wr;      // Указатель записи
    reg [WIDTH-1:0] rd;      // Указатель чтения
    assign wr_debug = wr;
    assign rd_debug = rd;

    // Сброс
    always @(posedge clk or posedge reset) begin //процедурный блок выполняется по положительному тактовому сигналу
        //Если сигналы выравнены, то тактовый сигнал будет равен лог. "1", значит можем работать, однако если он равен логическому "0", стоит проверить rest
        if (reset) begin // Здесь происходит сброс указателей при команде reset
            wr <= 0;
            rd <= 0;
            full <= 0;
            empty <= 1;
        end else begin
            // Добавление данных
            if (push && !full) begin //Мы можем положить что-то в память, если есть команда push, но при этом память не заполнена
                mem[wr] <= data_in;
                wr <= (wr + 1) % DEPTH; // контрлирует, что указатель ходит по кругу 8 битов
            end

            // Извлечение данных
            if (pop && !empty) begin //Мы можем извлечь командов pop тогда, когда память не пустая
                data_out <= mem[rd];
                rd <= (rd + 1) % DEPTH;
            end
            assign full = ((wr+1'b1) == rd);
            assign empty = (wr == rd);
        end
    end
endmodule
