SKEngine {
    classvar <default;

    var <clock;
    var <tempo;
    var <quant;
    var <tracks;
    var <scenes;
    var <mixer;
    var <safety;
    var <controller;
    var <currentScene;
    var <isRunning = false;

    *new { |tempo = 120, quant = 4|
        ^super.new.init(tempo, quant)
    }

    init { |tempoValue, quantValue|
        tempo = tempoValue;
        quant = quantValue;
        clock = TempoClock.new(tempo / 60);
        tracks = IdentityDictionary.new;
        scenes = IdentityDictionary.new;
        ^this
    }

    *default {
        ^default
    }

    *setDefault { |engine|
        default = engine;
        ^default
    }

    start {
        isRunning = true;
        ^this
    }

    stop {
        tracks.valuesDo(_.stop);
        isRunning = false;
        ^this
    }

    tempo_ { |bpm|
        tempo = bpm;
        clock.tempo = bpm / 60;
        ^tempo
    }

    addTrack { |track|
        tracks[track.name] = track;
        track.engine = this;
        ^track
    }

    removeTrack { |name|
        var track = tracks.removeAt(name);
        track.notNil.if { track.stop };
        ^track
    }

    track { |name|
        ^tracks[name]
    }

    addScene { |scene|
        scenes[scene.name] = scene;
        ^scene
    }

    scene { |name|
        ^scenes[name]
    }

    setScene { |name|
        var nextScene = scenes[name];
        nextScene.isNil.if {
            ("SKEngine: scene not found: " ++ name).warn;
            ^nil
        };

        clock.schedAbs(clock.nextBar(quant), {
            nextScene.applyTo(this);
            currentScene = nextScene;
            nil
        });

        ^nextScene
    }

    nextBar { |bars = 1|
        ^clock.nextBar(bars * quant)
    }

    panic {
        tracks.valuesDo(_.stop);
        safety.notNil.if(_.panic);
        ^this
    }
}