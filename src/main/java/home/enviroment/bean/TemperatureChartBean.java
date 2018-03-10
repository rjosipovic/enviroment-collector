/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.enviroment.bean;

import home.enviroment.entity.Mesurement;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LinearAxis;

/**
 *
 * @author roman
 */
@ManagedBean
public class TemperatureChartBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(TemperatureChartBean.class.getName());
    
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    
    private static final int TEMP_MIN = 10;
    private static final int TEMP_MAX = 50;

    private LineChartModel totalLineModel;
    private LineChartModel lastHourLineModel;

    @EJB
    private MesurementHandlerBean mesurementBean;

    @PostConstruct
    private void construct() {
        LOGGER.log(Level.FINER, "TemperatureChartBean created");
        createTotalLineModel();
        createLastHourLineModel();
    }
    
    private void createLastHourLineModel() {
        List<Mesurement> lastHourMesurements = mesurementBean.getLastHourMesurments();
        for(Mesurement mesurement : lastHourMesurements) {
            LOGGER.log(Level.INFO, "Date: " + mesurement.getMesureTime() + " value: " + mesurement.getTemperature());
        }
        lastHourLineModel = new LineChartModel();
        lastHourLineModel.setTitle("Last hour room temperature");
        lastHourLineModel.setLegendPosition("n");
        
        lastHourLineModel.addSeries(getChartSeries(lastHourMesurements));
        
        totalLineModel.getAxes().put(AxisType.X, getXAxis(formatter.format(lastHourMesurements.get(0).getMesureTime().getTime()-10000), formatter.format(new Date().getTime()+10000)));
        totalLineModel.getAxes().put(AxisType.Y, getYAxis(TEMP_MIN, TEMP_MAX));
    }
    
    private void createTotalLineModel() {
        List<Mesurement> allMesurments = mesurementBean.getAllMesurments();

        totalLineModel = new LineChartModel();
        totalLineModel.setTitle("Living room temperature");
        totalLineModel.setLegendPosition("n");
        
        totalLineModel.addSeries(getChartSeries(allMesurments));
        
        totalLineModel.getAxes().put(AxisType.X, getXAxis(formatter.format(allMesurments.get(0).getMesureTime().getTime()-10000), formatter.format(allMesurments.get(allMesurments.size()-1).getMesureTime().getTime()+10000)));
        totalLineModel.getAxes().put(AxisType.Y, getYAxis(TEMP_MIN, TEMP_MAX));
    }
    
    private Axis getYAxis(Object min, Object max) {
        LinearAxis yAxis = new LinearAxis();
        yAxis.setMin(min);
        yAxis.setMax(max);
        return yAxis;
    }
    
    private Axis getXAxis(Object min, Object max) {
        DateAxis dateAxis = new DateAxis("time");
        dateAxis.setTickFormat("%d-%m %H:%#M:%S");
        dateAxis.setMax(max);
        dateAxis.setMin(min);
        return dateAxis;
    }
    
    
    private ChartSeries getChartSeries(List<Mesurement> allMesurments) {
        ChartSeries series = new ChartSeries();
        series.setLabel("Temperature");

        for (Mesurement mesurement : allMesurments) {
            series.set(mesurement.getMesureTime().toString(), mesurement.getTemperature());
        }
        return series;
    }
    public LineChartModel getLastHourLineModel() {
        return lastHourLineModel;
    }

    public void setLastHourLineModel(LineChartModel lastHourLineModel) {
        this.lastHourLineModel = lastHourLineModel;
    }

    public LineChartModel getTotalLineModel() {
        return totalLineModel;
    }

    public void setTotalLineModel(LineChartModel totalLineModel) {
        this.totalLineModel = totalLineModel;
    }
}
