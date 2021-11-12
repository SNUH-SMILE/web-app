let TableGenerator = {

    //table body section
    addTblbody: function(th_list, data, tblObj, fnCallback) {
        console.log('table body add');
        var strTblBody = "";
        data.forEach((item=>{
            console.log('item :: ', item)

            if(fnCallback == undefined || fnCallback == ""){
                strTblBody += "<tr>";
            }else {
                strTblBody += '<tr onclick= "' + fnCallback + '">';
            }

            for(var i = 0; i < tblObj.length; i++){
                // var headId = th_list[i];

                if(tblObj[i].hidden){
                    strTblBody += '<td name="' + tblObj[i].id + '" hidden="true">';
                }else{
                    strTblBody += '<td name="' + tblObj[i].id + '">';
                }

                //type checkbox면 td에 추가
                if(tblObj[i].type == 'checkbox') {
                    if(item[tblObj[i].id] == undefined){
                        strTblBody += '<input type="checkbox" />';
                    }
                    else {
                        strTblBody += '<input type="checkbox"';
                        strTblBody += item[tblObj[i].id] == "Y"?" checked='true'":"";
                        strTblBody += '/>';
                    }
                }

                //type button 이면 td에 추가
                else if(tblObj[i].type == 'button'){
                    strTblBody += '<button';

                    //class 라는 속성을 지정했으면 class 추가하기 (button 태그에)
                    if(tblObj[i].class !== null) {
                        strTblBody += ' class = "' + tblObj[i].class + '"';
                    }

                    if(tblObj[i].fnCallback !== null) {
                        strTblBody += ' onclick="' + tblObj[i].fnCallback + '">' + tblObj[i].id + '</button>';
                    }
                    else {
                        strTblBody += '</button>';
                    }
                }

                //이외 type들은 td로 간주하고 그냥 값만 td에 추가한다
                else{
                    strTblBody += item[tblObj[i].id];
                }

                //td태그 닫아주기
                strTblBody += '</td>';
            }
            //tr태그 닫아주기
            strTblBody += '</tr>';

        }));
        return strTblBody;
    },

    //table init
    tableInit: function(tblName, data, tblObj, fnCallback) {
        const $table = $(document).find('#'+tblName);
        const $tblBody = $table.find('tbody');
        const $list_th = $table.find('th');

        if(data != null && data != undefined && data.length > 0){
            $tblBody.html(TableGenerator.addTblbody($list_th, data, tblObj, fnCallback));
        }else {
            $tblBody.html('<tr class="text-center"><td colspan="' + $list_th.length + '">데이터가 없습니다.</td></tr>');
        }

        // if(data != null && data != undefined) {
        //     pageNum==1?$tblBody.html(TableGenerator.addTblbody($list_th, data, tblObj)):$tblBody.append(TableGenerator.addTblbody($list_th, data, tblObj));
        // }
        // else {
        //     $tblBody.html('<tr class="text-center"><td colspan="' + $list_th.length + '">데이터가 없습니다.</td></tr>');
        // }

    }

} //end of Table_Prototype1 Object