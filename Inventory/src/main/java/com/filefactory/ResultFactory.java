package com.filefactory;

public class ResultFactory {
    public ResultGenerator getResult(boolean isError) {
        if (isError == false) {
            return new BillGenerator();
        } else if (isError == true) {
            return new ErrorGenerator();
        }
        return null;
    }
}