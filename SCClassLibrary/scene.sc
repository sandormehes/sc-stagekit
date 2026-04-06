Scene {
    var <name;
    var <trackConfigs;
    var <fxConfig;
    var <meta;

    *new { |name, trackConfigs = (), fxConfig = (), meta = ()|
        ^super.new.init(name, trackConfigs, fxConfig, meta)
    }

    init { |sceneName, tracksDict, fxDict, metaDict|
        name = sceneName;
        trackConfigs = tracksDict ? ();
        fxConfig = fxDict ? ();
        meta = metaDict ? ();
        ^this
    }

    applyTo { |engine|
        engine.tracks.keysValuesDo { |trackName, track|
            var cfg = trackConfigs[trackName];
            track.applyConfig(cfg);

            cfg.isNil.if {
                track.stop;
            } {
                track.isPlaying.if {
                    track.stop;
                };
                track.play(engine.quant);
            };
        };

        engine.mixer.notNil.if {
            engine.mixer.applyFXConfig(fxConfig);
        };

        ^this
    }

    trackConfig { |trackName|
        ^trackConfigs[trackName]
    }
}