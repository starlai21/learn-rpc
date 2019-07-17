package stage1.dynamic_proxy.service.impl;

import stage1.dynamic_proxy.service.BookApi;

public class BookApiImpl implements BookApi {
    @Override
    public void sell() {
        System.out.println("sell book...");
    }
}
