#!/data/data/com.linuxlator/files/usr/bin/bash
# Welcome banner
pkg install -y neofetch figlet 2>/dev/null || true
clear
figlet -f small LinuxLator
neofetch 2>/dev/null || echo "Welcome to LinuxLator!"

# Useful aliases
cat >> $PREFIX/etc/bash.bashrc <<'ALIASES'
alias ll='ls -alF'
alias la='ls -A'
alias update='pkg upgrade -y'
alias please='sudo'
echo "Tip: type 'proot-distro install alpine' for real Alpine Linux"
ALIASES
