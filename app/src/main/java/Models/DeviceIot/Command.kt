package Models.DeviceIot

data class DeviceCommands(
    val buttons: List<ButtonCommand>,
    val seekBars: List<SeekBarCommand>
)