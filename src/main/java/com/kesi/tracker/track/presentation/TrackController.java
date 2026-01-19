package com.kesi.tracker.track.presentation;


import com.kesi.tracker.track.application.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("track")
public class TrackController {
    private final TrackService trackService;

}
