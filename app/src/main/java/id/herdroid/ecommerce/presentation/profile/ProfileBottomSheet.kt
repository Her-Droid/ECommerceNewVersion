package id.herdroid.ecommerce.presentation.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.ecommerce.R
import id.herdroid.ecommerce.databinding.BottomSheetProfileBinding
import id.herdroid.ecommerce.presentation.cart.CartActivity
import id.herdroid.ecommerce.presentation.listOder.ListOrderActivity

@AndroidEntryPoint
class ProfileBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    private val getImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                viewModel.saveProfileImage(it)
            }
        }

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val shapeAppearanceModel = ShapeAppearanceModel.Builder()
                .setTopLeftCorner(CornerFamily.ROUNDED, 40f)
                .setTopRightCorner(CornerFamily.ROUNDED, 40f)
                .build()

            val materialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel).apply {
                fillColor = ContextCompat.getColorStateList(requireContext(), R.color.white)
            }

            it.background = materialShapeDrawable
        }

        viewModel.cartCount.observe(viewLifecycleOwner) {
            binding.tvCartCount.text = "Cart ($it items)"
        }

        viewModel.favoriteCount.observe(viewLifecycleOwner) {
            binding.tvFavoriteCount.text = "Favorite ($it items)"
        }

        viewModel.orderCount.observe(viewLifecycleOwner) {
            binding.tvOrderCount.text = "Orders ($it)"
        }

        viewModel.username.observe(viewLifecycleOwner) { username ->
            binding.tvUsername.text = username
        }

        binding.imgUploadIcon.setOnClickListener {
            getImage.launch("image/*")
        }

        binding.cardCart.setOnClickListener {
            startActivity(Intent(requireContext(), CartActivity::class.java))
        }

        binding.cardFavorite.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.favoriteFragment)
        }

        binding.cardOrders.setOnClickListener {
            startActivity(Intent(requireContext(), ListOrderActivity::class.java))
        }


        viewModel.profileImage.observe(viewLifecycleOwner) { uri ->
            Glide.with(this)
                .load(uri)
                .apply(
                    RequestOptions()
                        .transform(CircleCrop())
                        .placeholder(R.drawable.ic_profile)
                        .error(R.drawable.ic_profile)
                )
                .into(binding.imgProfile)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun show(fragmentManager: FragmentManager) {
            val profileFragment = ProfileBottomSheet()
            profileFragment.show(fragmentManager, profileFragment.tag)
        }
    }
}
