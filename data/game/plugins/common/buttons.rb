# The logout button mapping
LOGOUT_BUTTON = { :id => 182, :component => 6 }

# Bind the logout button
bind :btn, LOGOUT_BUTTON do
	player.logout
end