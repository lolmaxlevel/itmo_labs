/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 33.333333333333336, "KoPercent": 66.66666666666667};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.3325, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.0, 500, 1500, "2 requests"], "isController": false}, {"data": [0.0, 500, 1500, "1 requests"], "isController": false}, {"data": [0.9975, 500, 1500, "3 requests"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 600, 400, 66.66666666666667, 818.6466666666659, 315, 1480, 815.0, 1212.9, 1217.0, 1264.99, 9.923424242925424, 2.23858496105056, 1.5505350379570977], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["2 requests", 200, 200, 100.0, 825.1200000000003, 716, 1083, 815.0, 856.9, 860.0, 881.9000000000001, 3.3288394001431403, 0.7509393568682279, 0.5201311562723656], "isController": false}, {"data": ["1 requests", 200, 200, 100.0, 1208.0499999999995, 1117, 1480, 1207.0, 1256.9, 1262.0, 1294.7900000000002, 3.307808080975142, 0.7461949870168533, 0.5168450126523659], "isController": false}, {"data": ["3 requests", 200, 0, 0.0, 422.77, 315, 678, 407.0, 463.0, 467.0, 472.97, 3.3510941322341745, 0.7559597114707952, 0.5236084581615897], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["The operation lasted too long: It took 1&nbsp;203 milliseconds, but should not have lasted longer than 710 milliseconds.", 9, 2.25, 1.5], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;260 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 794 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 856 milliseconds, but should not have lasted longer than 710 milliseconds.", 4, 1.0, 0.6666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 809 milliseconds, but should not have lasted longer than 710 milliseconds.", 3, 0.75, 0.5], "isController": false}, {"data": ["The operation lasted too long: It took 814 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;255 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 769 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;193 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 834 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;225 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;138 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;265 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 866 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;257 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 749 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;272 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;128 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;215 milliseconds, but should not have lasted longer than 710 milliseconds.", 4, 1.0, 0.6666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 750 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;153 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;185 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 868 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;156 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;218 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;205 milliseconds, but should not have lasted longer than 710 milliseconds.", 13, 3.25, 2.1666666666666665], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;208 milliseconds, but should not have lasted longer than 710 milliseconds.", 12, 3.0, 2.0], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;211 milliseconds, but should not have lasted longer than 710 milliseconds.", 8, 2.0, 1.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 846 milliseconds, but should not have lasted longer than 710 milliseconds.", 3, 0.75, 0.5], "isController": false}, {"data": ["The operation lasted too long: It took 849 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;131 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 839 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;295 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 836 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 804 milliseconds, but should not have lasted longer than 710 milliseconds.", 7, 1.75, 1.1666666666666667], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;176 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 807 milliseconds, but should not have lasted longer than 710 milliseconds.", 6, 1.5, 1.0], "isController": false}, {"data": ["The operation lasted too long: It took 842 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 827 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;259 milliseconds, but should not have lasted longer than 710 milliseconds.", 3, 0.75, 0.5], "isController": false}, {"data": ["The operation lasted too long: It took 734 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 817 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 811 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 852 milliseconds, but should not have lasted longer than 710 milliseconds.", 7, 1.75, 1.1666666666666667], "isController": false}, {"data": ["The operation lasted too long: It took 837 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 858 milliseconds, but should not have lasted longer than 710 milliseconds.", 5, 1.25, 0.8333333333333334], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;175 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 848 milliseconds, but should not have lasted longer than 710 milliseconds.", 5, 1.25, 0.8333333333333334], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;262 milliseconds, but should not have lasted longer than 710 milliseconds.", 4, 1.0, 0.6666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 851 milliseconds, but should not have lasted longer than 710 milliseconds.", 10, 2.5, 1.6666666666666667], "isController": false}, {"data": ["The operation lasted too long: It took 812 milliseconds, but should not have lasted longer than 710 milliseconds.", 3, 0.75, 0.5], "isController": false}, {"data": ["The operation lasted too long: It took 808 milliseconds, but should not have lasted longer than 710 milliseconds.", 14, 3.5, 2.3333333333333335], "isController": false}, {"data": ["The operation lasted too long: It took 774 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;223 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 847 milliseconds, but should not have lasted longer than 710 milliseconds.", 5, 1.25, 0.8333333333333334], "isController": false}, {"data": ["The operation lasted too long: It took 802 milliseconds, but should not have lasted longer than 710 milliseconds.", 4, 1.0, 0.6666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;219 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;258 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 799 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;213 milliseconds, but should not have lasted longer than 710 milliseconds.", 11, 2.75, 1.8333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;480 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;126 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 735 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;155 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;271 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 857 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 803 milliseconds, but should not have lasted longer than 710 milliseconds.", 4, 1.0, 0.6666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 758 milliseconds, but should not have lasted longer than 710 milliseconds.", 3, 0.75, 0.5], "isController": false}, {"data": ["The operation lasted too long: It took 860 milliseconds, but should not have lasted longer than 710 milliseconds.", 4, 1.0, 0.6666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;256 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 835 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 870 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;209 milliseconds, but should not have lasted longer than 710 milliseconds.", 5, 1.25, 0.8333333333333334], "isController": false}, {"data": ["The operation lasted too long: It took 716 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;162 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;169 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;214 milliseconds, but should not have lasted longer than 710 milliseconds.", 7, 1.75, 1.1666666666666667], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;127 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 855 milliseconds, but should not have lasted longer than 710 milliseconds.", 8, 2.0, 1.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 850 milliseconds, but should not have lasted longer than 710 milliseconds.", 6, 1.5, 1.0], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;204 milliseconds, but should not have lasted longer than 710 milliseconds.", 14, 3.5, 2.3333333333333335], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;159 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 813 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 882 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;199 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;117 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 773 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;167 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 845 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 872 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;212 milliseconds, but should not have lasted longer than 710 milliseconds.", 4, 1.0, 0.6666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 778 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 843 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;202 milliseconds, but should not have lasted longer than 710 milliseconds.", 10, 2.5, 1.6666666666666667], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;157 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 775 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 820 milliseconds, but should not have lasted longer than 710 milliseconds.", 3, 0.75, 0.5], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;170 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 801 milliseconds, but should not have lasted longer than 710 milliseconds.", 4, 1.0, 0.6666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 865 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 785 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;083 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;197 milliseconds, but should not have lasted longer than 710 milliseconds.", 3, 0.75, 0.5], "isController": false}, {"data": ["The operation lasted too long: It took 863 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 800 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 806 milliseconds, but should not have lasted longer than 710 milliseconds.", 12, 3.0, 2.0], "isController": false}, {"data": ["The operation lasted too long: It took 810 milliseconds, but should not have lasted longer than 710 milliseconds.", 5, 1.25, 0.8333333333333334], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;206 milliseconds, but should not have lasted longer than 710 milliseconds.", 11, 2.75, 1.8333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 786 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;210 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;191 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 859 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;217 milliseconds, but should not have lasted longer than 710 milliseconds.", 3, 0.75, 0.5], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;207 milliseconds, but should not have lasted longer than 710 milliseconds.", 10, 2.5, 1.6666666666666667], "isController": false}, {"data": ["The operation lasted too long: It took 816 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 787 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 853 milliseconds, but should not have lasted longer than 710 milliseconds.", 3, 0.75, 0.5], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;220 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;122 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;201 milliseconds, but should not have lasted longer than 710 milliseconds.", 7, 1.75, 1.1666666666666667], "isController": false}, {"data": ["The operation lasted too long: It took 854 milliseconds, but should not have lasted longer than 710 milliseconds.", 6, 1.5, 1.0], "isController": false}, {"data": ["The operation lasted too long: It took 777 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 844 milliseconds, but should not have lasted longer than 710 milliseconds.", 3, 0.75, 0.5], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;139 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 805 milliseconds, but should not have lasted longer than 710 milliseconds.", 11, 2.75, 1.8333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;171 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;264 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 771 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;216 milliseconds, but should not have lasted longer than 710 milliseconds.", 6, 1.5, 1.0], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;187 milliseconds, but should not have lasted longer than 710 milliseconds.", 2, 0.5, 0.3333333333333333], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;168 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;274 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}, {"data": ["The operation lasted too long: It took 1&nbsp;200 milliseconds, but should not have lasted longer than 710 milliseconds.", 1, 0.25, 0.16666666666666666], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 600, 400, "The operation lasted too long: It took 808 milliseconds, but should not have lasted longer than 710 milliseconds.", 14, "The operation lasted too long: It took 1&nbsp;204 milliseconds, but should not have lasted longer than 710 milliseconds.", 14, "The operation lasted too long: It took 1&nbsp;205 milliseconds, but should not have lasted longer than 710 milliseconds.", 13, "The operation lasted too long: It took 1&nbsp;208 milliseconds, but should not have lasted longer than 710 milliseconds.", 12, "The operation lasted too long: It took 806 milliseconds, but should not have lasted longer than 710 milliseconds.", 12], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["2 requests", 200, 200, "The operation lasted too long: It took 808 milliseconds, but should not have lasted longer than 710 milliseconds.", 14, "The operation lasted too long: It took 806 milliseconds, but should not have lasted longer than 710 milliseconds.", 12, "The operation lasted too long: It took 805 milliseconds, but should not have lasted longer than 710 milliseconds.", 11, "The operation lasted too long: It took 851 milliseconds, but should not have lasted longer than 710 milliseconds.", 10, "The operation lasted too long: It took 855 milliseconds, but should not have lasted longer than 710 milliseconds.", 8], "isController": false}, {"data": ["1 requests", 200, 200, "The operation lasted too long: It took 1&nbsp;204 milliseconds, but should not have lasted longer than 710 milliseconds.", 14, "The operation lasted too long: It took 1&nbsp;205 milliseconds, but should not have lasted longer than 710 milliseconds.", 13, "The operation lasted too long: It took 1&nbsp;208 milliseconds, but should not have lasted longer than 710 milliseconds.", 12, "The operation lasted too long: It took 1&nbsp;206 milliseconds, but should not have lasted longer than 710 milliseconds.", 11, "The operation lasted too long: It took 1&nbsp;213 milliseconds, but should not have lasted longer than 710 milliseconds.", 11], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
