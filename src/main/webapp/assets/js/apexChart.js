let options;
let fileName;
/**
 * 혈압 데이터 양식
 * {x: '2021-01-01', y: ['150', '150', '60', '60'] }
 */
let bpDataArray = [];

/**
 * 혈압 데이터 넣기
 * @param dataDate = 측정날짜
 * @param max      = SBP
 * @param min      = DBP
 */
function pushBpData(dataDate, max, min) {
    bpDataArray.push(
        {
            x: dataDate,
            y: [Number(min),Number(min), Number(max), Number(max)]
        }
    );
};

/**
 * 체온/맥박/호흡/산소포화도 데이터 양식
 * {x: '2021-01-01', y: '99' }
 */
let btDataArray = [];
/**
 * 체온 데이터 넣기
 * @param dataDate   = 측정날짜
 * @param dataValue  = 측정값
 */
function pushBtData(dataDate, dataValue) {
    btDataArray.push(
        {
            x: dataDate,
            y: Number(dataValue)
        }
    );
};
/**
 * 맥박 데이터 넣기
 * @param dataDate   = 측정날짜
 * @param dataValue  = 측정값
 */
let prDataArray = [];
function pushPrData(dataDate, dataValue) {
    prDataArray.push(
        {
            x: dataDate,
            y: Number(dataValue)
        }
    );
};
/**
 * 호흡 데이터 넣기
 * @param dataDate   = 측정날짜
 * @param dataValue  = 측정값
 */
let rrDataArray = [];
function pushRrData(dataDate, dataValue) {
    rrDataArray.push(
        {
            x: dataDate,
            y: Number(dataValue)
        }
    );
};
/**
 * 산소포화도 데이터 넣기
 * @param dataDate   = 측정날짜
 * @param dataValue  = 측정값
 */
let spo2DataArray = [];
function pushSpo2Data(dataDate, dataValue) {
    spo2DataArray.push(
        {
            x: dataDate,
            y: Number(dataValue)
        }
    );
};
function makeChart(dataStatus) {
    if (dataStatus === false) {
        document.querySelector("#chart").innerHTML = '';
        patientDetailVsDataTableBody.html('')
        prDataArray   = [];
        btDataArray   = [];
        rrDataArray   = [];
        spo2DataArray = [];
        bpDataArray   = [];
    }

    options = {
        series: [],
        chart: {
            animate:{
                enabled:true
            },
            id:'vitalSignChart',
            width: '100%',
            height: 500,
            type: 'line',
            toolbar: {
                show: true,
                tools: {
                    customIcons: [{
                        icon: '<i class="bi bi-chat-square mt-1" id="apexChartCustomIcon"></i>',
                        index: -6,
                        title: 'on/off DataLabels',
                        class: 'custom-icon d-flex justify-content-center align-items-center',
                        click: function (chart, options, e) {
                            showDataLabels();
                        }
                    }]
                },
                export: {
                    svg: {
                        filename: fileName,
                    },
                    png: {
                        filename: fileName,
                    }
                }
            }
        },
        title: {
            text: 'V/S Chart',
            align: 'left'
        },
        stroke: {
            curve: 'smooth',
            color:'rgba(154,20,255,0.25)',
            width: [3, 3, 3, 3, 1]
        },
        markers:{
            size:[4,4,4,4,0]
        },
        dataLabels: {
            enabled: true,
            textAnchor:'middle',
            offsetY: -10,
            background: {
                enabled: true,
                opacity:0.8,
            },
            formatter: function(value, { seriesIndex, dataPointIndex, w }) {
                if(seriesIndex === 4){
                    var h = w.globals.seriesCandleH[seriesIndex][dataPointIndex]
                    var l = w.globals.seriesCandleL[seriesIndex][dataPointIndex]
                    return h+'/'+l;
                }
                else{
                    return value;
                }
            }
        },
        plotOptions: {
            candlestick: {
                colors: {
                    downward: 'rgba(154,20,255,0.1)',
                    upward: 'rgba(154,20,255,0.1)'
                },
                wick: {
                    useFillColor: true,
                }
            },
            bar: {
                columnWidth: '50%'
            }
        },
        noData: {
            text: 'V/S 데이터가 없습니다.',
            align: 'center',
            verticalAlign: 'middle',
            offsetX: 0,
            offsetY: 0,
            style: {
                color: 'black',
                fontSize: '14px',
                fontFamily: undefined
            }
        },
        legend: {
            show: true,
            position: 'bottom',
            itemMargin: {
                horizontal: 5,
                vertical: 20
            },
        },
        tooltip: {
            x: {
                format: "dd.MM.yyyy HH:mm"
            },
            shared: true,
            custom: [

                function ({ series, seriesIndex, dataPointIndex, w }) {
                    let date = new Date(prDataArray[dataPointIndex].x);
                    let dateMonth = date.getMonth() + 1;
                    let dateDay = date.getDate();
                    let dateHour = date.getHours();
                    let dateMinute = date.getMinutes();
                    return '<div class="card">'
                        + '<div class="card-header" style="background-color: white; color:black"><i class="bi bi-circle-fill me-3" style="color: dodgerblue"></i>'
                        + dateMonth + '/' + dateDay + ' ' + dateHour + ':' + dateMinute
                        + '</div>'
                        + '맥박:' + series[seriesIndex][dataPointIndex]
                        + '</div>'
                },
                function ({ series, seriesIndex, dataPointIndex, w }) {
                    let date = new Date(btDataArray[dataPointIndex].x);
                    let dateMonth = date.getMonth() + 1;
                    let dateDay = date.getDate();
                    let dateHour = date.getHours();
                    let dateMinute = date.getMinutes();
                    return '<div class="card">'
                        + '<div class="card-header" style="background-color: white;color:black"><i class="bi bi-circle-fill me-3" style="color: #5bffa4"></i>'
                        + dateMonth + '/' + dateDay + ' ' + dateHour + ':' + dateMinute
                        + '</div>'
                        + '체온:' + series[seriesIndex][dataPointIndex]
                        + '</div>'
                },
                function ({ series, seriesIndex, dataPointIndex, w }) {
                    let date = new Date(rrDataArray[dataPointIndex].x);
                    let dateMonth = date.getMonth() + 1;
                    let dateDay = date.getDate();
                    let dateHour = date.getHours();
                    let dateMinute = date.getMinutes();
                    return '<div class="card">'
                        + '<div class="card-header" style="background-color: white;color:black"><i class="bi bi-circle-fill me-3" style="color: #ffe95b"></i>'
                        + dateMonth + '/' + dateDay + ' ' + dateHour + ':' + dateMinute
                        + '</div>'
                        + '호흡:' + series[seriesIndex][dataPointIndex]
                        + '</div>'
                },
                function ({ series, seriesIndex, dataPointIndex, w }) {
                    let date = new Date(spo2DataArray[dataPointIndex].x);
                    let dateMonth = date.getMonth() + 1;
                    let dateDay = date.getDate();
                    let dateHour = date.getHours();
                    let dateMinute = date.getMinutes();
                    return '<div class="card">'
                        + '<div class="card-header" style="background-color: white;color:black"><i class="bi bi-circle-fill me-3" style="color: #ff5b7c"></i>'
                        + dateMonth + '/' + dateDay + ' ' + dateHour + ':' + dateMinute
                        + '</div>'
                        + '산소포화도:' + series[seriesIndex][dataPointIndex]
                        + '</div>'
                },
                function ({ series, seriesIndex, dataPointIndex, w }) {
                    var o = w.globals.seriesCandleO[seriesIndex][dataPointIndex]
                    var h = w.globals.seriesCandleH[seriesIndex][dataPointIndex]
                    var l = w.globals.seriesCandleL[seriesIndex][dataPointIndex]
                    var c = w.globals.seriesCandleC[seriesIndex][dataPointIndex]
                    let date = new Date(bpDataArray[dataPointIndex].x);
                    let dateMonth = date.getMonth() + 1;
                    let dateDay = date.getDate();
                    let dateHour = date.getHours();
                    let dateMinute = date.getMinutes();
                    return '<div class="card">'
                        + '<div class="card-header" style="background-color: white;color:black"><i class="bi bi-circle-fill me-3" style="color: #e08ef5"></i>'
                        + dateMonth + '/' + dateDay + ' ' + dateHour + ':' + dateMinute
                        + '</div>'
                        + '혈압:' + h + '/' + l
                        + '</div>'
                }
            ]
        },
        yaxis: {
            show: true,
            min:0,
            forceNiceScale:true,
            decimalsInFloat: 0,
            tickAmount: 4,

        },
        xaxis: {
            type: 'datetime',
            labels: {
                format: 'MM/dd HH:mm',
                datetimeUTC: false,
                trim:false
            },
        },
    };
    chart = new ApexCharts(document.querySelector("#chart"), options);
    chart.render();
}
let dataLabelsStatus = true;
function showDataLabels() {
    if(dataLabelsStatus === false){
        chart.updateOptions({
            dataLabels: {
                enabled: true,
                textAnchor:'middle',
                offsetY: -10,
                background: {
                    enabled: true,
                    opacity:0.8,
                },
                formatter: function(value, { seriesIndex, dataPointIndex, w }) {
                    if(seriesIndex === 4){
                        var h = w.globals.seriesCandleH[seriesIndex][dataPointIndex]
                        var l = w.globals.seriesCandleL[seriesIndex][dataPointIndex]
                        return h+'/'+l;
                    }
                    else{
                        return value;
                    }
                }
            }
        })
        dataLabelsStatus=true;
    }
    else{
        chart.updateOptions({
            dataLabels: {
                enabled: false,
            }
        })
        dataLabelsStatus=false;
    }
}
function updateChartSeriesData() {
    chart.updateOptions({
        series: [
            {
                name: '맥박',
                type: 'line',
                data: prDataArray.reverse()

            },
            {
                name: '체온',
                type: 'line',
                data: btDataArray.reverse()
            },
            {
                name: '호흡',
                type: 'line',
                data: rrDataArray.reverse()
            },
            {
                name: '산소포화도',
                type: 'line',
                data: spo2DataArray.reverse()
            }
            ,
            {
                name: '혈압',
                type: 'candlestick',
                data: bpDataArray.reverse()
            }
        ]
    });

}