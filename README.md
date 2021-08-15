# robottask

A simple Spring Boot application to render a robot position on a grid after executing a script with the following
commands:

```
POSITION X Y DIRECTION // DIRECTION - ONE OF NORTH, EAST, SOUTH, WEST
FORWARD N // move N steps further
RIGHT // turn right
LEFT // turn left
TURNAROUND // turn around
WAIT // do nothing
```

## Usage

```shell
$ make build // compile
$ make run // start app
$ make test // run tests
```

## Notes on probable improvements

1. [backend] Right now the grid size is not configurable in UI, but can be customized in controller.
2. [backend] Integration tests (form submission) were not implemented.
3. [backend] It's far better to parse & execute script in background (Spring Async) and report back to client: client
   uploads the script and gets task ID, then polls task status until the task is processed, but more time and effort
   would be required to implement such a scheme.
4. [frontend] Grid is not mobile-friendly and the size of cells is hardcoded.
5. [frontend] With JS canvas API one can actually flip the robot icon, but it required less time to just provide 4 icons for
   all cardinal directions.
6. [frontend] JS code is now in HTML file, not in a separate one.