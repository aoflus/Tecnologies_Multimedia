/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.beust.jcommander.Parameter;

/**
 *
 * @author vikos
 */
public class Settings {

    @Parameter(names = { "-i", "--input" }, description = "Fitxer entrada", required = false)
    private String input;

    @Parameter(names = { "-o", "--output" }, description = "--output  <path to file> ", required = false)
    private String output;

    @Parameter(names = { "-e", "--encode" }, description = "--encode  ", required = false)
    private String encode;
    
    @Parameter(names = { "-d", "--decode" }, description = "--decode  ", required = false)
    private String decode;
    
    @Parameter(names = "--fps", description = "--fps  ", required = false)
    private String fps;
    
    @Parameter(names = "--binarization", description = "--fps  ", required = false)
    private String binarization;
    
    @Parameter(names = "--negative", description = "negative filter", required = false)
    private String negative;
    
    @Parameter(names = "--averaging", description = "averaging  filter", required = false)
    private String averaging;
    
    @Parameter(names = "--nTiles", description = "nTiles filter ", required = false)
    private String nTiles;
    
    @Parameter(names = "--seekRange", description = "seekRange filter ", required = false)
    private String seekRange;
    
    @Parameter(names = "--GOP", description = "GOP filter ", required = false)
    private String GOP;
        
    @Parameter(names = "--quality", description = "--quality filter ", required = false)
    private String quality;
    
    @Parameter(names = { "-b", "--batch" }, description = "--batch filter ", required = false)
    private String batch;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getDecode() {
        return decode;
    }

    public void setDecode(String decode) {
        this.decode = decode;
    }

    public String getFps() {
        return fps;
    }

    public void setFps(String fps) {
        this.fps = fps;
    }

    public String getBinarization() {
        return binarization;
    }

    public void setBinarization(String binarization) {
        this.binarization = binarization;
    }

    public String getNegative() {
        return negative;
    }

    public void setNegative(String negative) {
        this.negative = negative;
    }

    public String getAveraging() {
        return averaging;
    }

    public void setAveraging(String averaging) {
        this.averaging = averaging;
    }

    public String getnTiles() {
        return nTiles;
    }

    public void setnTiles(String nTiles) {
        this.nTiles = nTiles;
    }

    public String getSeekRange() {
        return seekRange;
    }

    public void setSeekRange(String seekRange) {
        this.seekRange = seekRange;
    }

    public String getGOP() {
        return GOP;
    }

    public void setGOP(String GOP) {
        this.GOP = GOP;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    
    
}