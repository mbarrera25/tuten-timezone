package com.pruebaTuten.TimeUTC;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api")

public class Controller {
    /***
     *http://localhost:8080/api/timezone
     * Parametros: {"dato1":"18:43:00",
     *              "dato2":"-3"
     *              }
     *
     * */
    @PostMapping("timezone")
    public HashMap<String,Object> show(@ApiBodyObject(clazz = String.class) @RequestBody String json) throws ParseException, JsonProcessingException {
        Map<String, Object> params = new ObjectMapper().readerFor(Map.class).readValue(json);
        String horaActual 	= (params.containsKey("dato1") && params.get("dato1") != null) ? params.get("dato1").toString() : null;
        String diferencia 	= (params.containsKey("dato2") && params.get("dato2") != null) ? params.get("dato2").toString(): null;

        HashMap<String,Object>resp = new HashMap<>();
        HashMap<String,Object> response= new HashMap<>();
        String[] hym = diferencia.split("\\.");


        Integer hrs = null;
        Integer min = null;
            hrs = Integer.valueOf(hym[0]);
        if (hym.length>1){
            min = Integer.valueOf(hym[1]);
        }
       TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        if (hrs!=null ) {
            calendar.add(calendar.HOUR, hrs);
        }else if ( min!=null){
            calendar.add(calendar.MINUTE,min);
        }else{
            calendar.add(calendar.HOUR, Integer.valueOf(diferencia));
        }
        String newZealandTime = formatter.format(calendar.getTime());

        resp.put("time",newZealandTime);
        resp.put("timezone","UTC");
        response.put("response",resp);

        return response;

    }


}
