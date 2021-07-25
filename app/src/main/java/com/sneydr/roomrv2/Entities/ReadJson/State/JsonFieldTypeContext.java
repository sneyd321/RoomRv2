package com.sneydr.roomrv2.Entities.ReadJson.State;

public class JsonFieldTypeContext<T> {

    private JsonFieldTypeState<T> jsonFieldTypeState;

    public JsonFieldTypeContext() {
        this.jsonFieldTypeState = new StringState<T>();
    }

    public void setState(JsonFieldTypeState<T> jsonFieldTypeState) { this.jsonFieldTypeState = jsonFieldTypeState; }

    public JsonFieldTypeState<T> getState() { return jsonFieldTypeState; }


}
