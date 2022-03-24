package com.moviesdb.actor;

import java.util.List;

public record ActorsViewModel(List<Actor> actors,
                              int pageCount,
                              int pageNumber) {
}
