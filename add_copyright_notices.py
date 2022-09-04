'''
EDI: Extended Debug Info
Copyright (C) 2022  Georg Kollegger(TheDuggy/CoderTheDuggy)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
'''
import os

copyrightNotice = "/*\n" \
                  " * EDI: Extended Debug Info\n" \
                  " * Copyright (C) 2022  Georg Kollegger(TheDuggy/CoderTheDuggy)\n * \n" \
                  " * This program is free software: you can redistribute it and/or modify\n" \
                  " * it under the terms of the GNU General Public License as published by\n" \
                  " * the Free Software Foundation, either version 3 of the License, or\n" \
                  " * (at your option) any later version.\n * \n" \
                  " * This program is distributed in the hope that it will be useful,\n" \
                  " * but WITHOUT ANY WARRANTY; without even the implied warranty of\n" \
                  " * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" \
                  " * GNU General Public License for more details.\n" \
                  " * You should have received a copy of the GNU General Public License\n" \
                  " * along with this program.  If not, see <http://www.gnu.org/licenses/>.\n" \
                  " */\n"
filecounter = 0
for path, subdirs, files in os.walk("./src/"):
    for name in files:
        file = os.path.join(path, name)
        lines = []
        print("Adding copyright-notice to " + file + "... ", end="")
        with open(file, "r") as fR:
            lines = fR.readlines()
        open(file, "w").close()
        with open(file, "a") as fW:
            fW.write(copyrightNotice)
            fW.writelines(lines)
        print("finished!")
        filecounter += 1
print("Added copyright-notice to " + str(filecounter) + " files!")
