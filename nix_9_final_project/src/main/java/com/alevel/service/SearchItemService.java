package com.alevel.service;

import java.util.List;

public interface SearchItemService {

    List<String> fetchSuggestions(String query);
}
