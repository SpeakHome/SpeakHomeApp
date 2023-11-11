package Models.DeviceIot

sealed class CommandItem {
    data class ButtonItem(val command: ButtonCommand): CommandItem()
    data class SeekBarItem(val command: SeekBarCommand): CommandItem()
}